package com.eventdriven.producer.Order.Infrastructure.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventdriven.producer.Order.Application.AddOrderLines;
import com.eventdriven.producer.Order.Application.CreateOrder;
import com.eventdriven.producer.Order.Application.DeleteOrderLine;
import com.eventdriven.producer.Order.Application.GetOrder;
import com.eventdriven.producer.Order.Application.GetOrders;
import com.eventdriven.producer.Order.Application.SetCloseStatusToOrder;
import com.eventdriven.producer.Order.Application.UpdateOrderAddress;
import com.eventdriven.producer.Order.Application.Service.CreateOrderRequest;
import com.eventdriven.producer.Order.Application.Service.OrderResponse;
import com.eventdriven.producer.Order.Domain.ValueObject.Address;
import com.eventdriven.producer.Order.Domain.ValueObject.LineItem;
import com.eventdriven.producer.Shared.Domain.ValueObject.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Tag(name = "Order demo producer Rest API", description = "Demo producer to publish events into Kafka topic")
@RequestMapping(path = "/v1/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderSpringRestController {
    @Autowired
    private CreateOrder createOrderUseCase;

    @Autowired
    private GetOrders getOrdersUseCase;

    @Autowired
    private GetOrder getOrderUseCase;

    @Autowired
    private AddOrderLines addLinesUseCase;

    @Autowired
    private DeleteOrderLine deleteLineUseCase;

    @Autowired
    private UpdateOrderAddress updateOrderAddressUseCase;

    @Autowired
    private SetCloseStatusToOrder setCloseToOrder;
    
    @PostMapping()
    @Operation(method = "Post", summary = "Create new customer order")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Order has been register"),
        @ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Server error while process", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest requestBody) {
        OrderResponse customerOrder = this.createOrderUseCase.invoke(requestBody);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(customerOrder);
    }

    @GetMapping(path = "/{id}", consumes = MediaType.ALL_VALUE)
    @Operation(method = "Get", summary = "Get the order by id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Return the order found"),
        @ApiResponse(responseCode = "404", description = "When no orders found with the Id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<OrderResponse> getOrder(@Parameter(example = "12", description = "The order id") @PathVariable Long id) {
        OrderResponse order = this.getOrderUseCase.invoke(id);

        return ResponseEntity.ok(order);
    }


    @GetMapping(path = "", consumes = MediaType.ALL_VALUE)
    @Operation(method = "Get", summary = "Get all the orders with criteria")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Return a list of orders"),
        @ApiResponse(responseCode = "500", description = "Server error while process", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<OrderResponse>> getAllOrders(
        @Parameter(description = "[optional] Address to looking for") @RequestParam(required = false) String address
    ) {
        List<OrderResponse> orders = this.getOrdersUseCase.invoke(address);
        
        return ResponseEntity.ok(orders);
    }

    @PutMapping(path = "/{id}/line")
    @Operation(method = "Put", summary = "Add new lines item to order by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "When lines were added"),
        @ApiResponse(responseCode = "404", description = "When no orders found with the Id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Server error while process", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OrderResponse> addLinesItems(    
        @Parameter(example = "12", description = "The order id") @PathVariable Long id,
        @Parameter(description = "An order new line") @RequestBody List<LineItem> linesItems
    ) {
        OrderResponse order = this.addLinesUseCase.invoke(id, linesItems);

        return ResponseEntity.ok(order);
    }

    @DeleteMapping(path = "/{id}/line", consumes = MediaType.ALL_VALUE)
    @Operation(method = "Delete", summary = "Delete line from order")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "When line were removed"),
        @ApiResponse(responseCode = "404", description = "When no orders found with the Id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Server error while process", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> deleteLinesItems(    
        @Parameter(example = "12", description = "The order id") @PathVariable Long id,
        @Parameter(example = "1", description = "The order line row position to remove") @RequestParam Integer listItemPosition
    ) {
        this.deleteLineUseCase.invoke(id, listItemPosition);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{id}")
    @Operation(method = "Patch", summary = "Change the order address")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Address were updated"),
        @ApiResponse(responseCode = "404", description = "When no orders found with the Id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Server error while process", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OrderResponse> updateAddress(    
        @Parameter(example = "12", description = "The order id") @PathVariable Long id,
        @Parameter(description = "An order new line") @RequestBody Address address
    ) {
        OrderResponse order = this.updateOrderAddressUseCase.invoke(id, address);

        return ResponseEntity.ok(order);
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.ALL_VALUE)
    @Operation(method = "Patch", summary = "Set the order as closed")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Order was closed"),
        @ApiResponse(responseCode = "404", description = "When no orders found with the Id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Server error while process", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OrderResponse> closeOrder(    
        @Parameter(example = "12", description = "The order id") @PathVariable Long id
    ) {
        OrderResponse order = this.setCloseToOrder.invoke(id);

        return ResponseEntity.ok(order);
    }
}
