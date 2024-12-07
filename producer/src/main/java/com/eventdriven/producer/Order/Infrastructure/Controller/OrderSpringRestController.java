package com.eventdriven.producer.Order.Infrastructure.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventdriven.producer.Order.Application.CreateOrder;
import com.eventdriven.producer.Order.Application.Service.OrderRequest;
import com.eventdriven.producer.Order.Application.Service.OrderResponse;
import com.eventdriven.producer.Order.Domain.Address;
import com.eventdriven.producer.Shared.Application.Service.ErrorResponse;
import com.eventdriven.producer.Shared.Domain.LineItem;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
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
    
    @PostMapping()
    @Operation(method = "Post", summary = "Create new customer order")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Order has been register"),
        @ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Server error while process", content = @Content())
    })
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest requestBody) {
        CreateOrder useCase = new CreateOrder();

        OrderResponse customerOrder = useCase.invoke(requestBody);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(customerOrder);
    }

    @GetMapping(path = "/{id}", consumes = MediaType.ALL_VALUE)
    @Operation(method = "Get", summary = "Get the order by id")
    @ApiResponses({
        @ApiResponse(responseCode = "501", description = "Not implemented"),
    })
    public ResponseEntity<ErrorResponse> getMethodName(@Parameter(example = "12", description = "The order id") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ErrorResponse(HttpStatus.NOT_IMPLEMENTED.value(), "This feature is not implemented."));
    }


    @GetMapping(path = "", consumes = MediaType.ALL_VALUE)
    @Operation(method = "Get", summary = "Get all the orders with criteria")
    @ApiResponses({
        @ApiResponse(responseCode = "501", description = "Not implemented"),
    })
    public ResponseEntity<ErrorResponse> getAllMethodName(
        @Parameter(example = "12", description = "The order id") @RequestParam(required = false) Long id, 
        @Parameter(example = "dream street, 12", description = "Address to looking for") @RequestParam(required = false) String address
    ) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ErrorResponse(HttpStatus.NOT_IMPLEMENTED.value(), "This feature is not implemented."));
    }

    @PutMapping(path = "/{id}/line")
    @Operation(method = "Put", summary = "Add new lines item to order by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "501", description = "Not implemented"),
    })
    public ResponseEntity<ErrorResponse> addLinesItemsMethodName(    
        @Parameter(example = "12", description = "The order id") @PathVariable Long id,
        @Parameter(description = "An order new line") @RequestBody List<LineItem> linesItems
    ) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ErrorResponse(HttpStatus.NOT_IMPLEMENTED.value(), "This feature is not implemented."));
    }

    @DeleteMapping(path = "/{id}/line", consumes = MediaType.ALL_VALUE)
    @Operation(method = "Delete", summary = "Delete line from order")
    @ApiResponses({
        @ApiResponse(responseCode = "501", description = "Not implemented"),
    })
    public ResponseEntity<ErrorResponse> deleteLinesItemsMethodName(    
        @Parameter(example = "12", description = "The order id") @PathVariable Long id,
        @Parameter(example = "1", description = "The order line row position to remove") @RequestParam Integer listItemPosition
    ) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ErrorResponse(HttpStatus.NOT_IMPLEMENTED.value(), "This feature is not implemented."));
    }

    @PatchMapping(path = "/{id}")
    @Operation(method = "Patch", summary = "Change the order address")
    @ApiResponses({
        @ApiResponse(responseCode = "501", description = "Not implemented"),
    })
    public ResponseEntity<ErrorResponse> updatePartialAddressMethodName(    
        @Parameter(example = "12", description = "The order id") @PathVariable Long id,
        @Parameter(description = "An order new line") @RequestBody Address address
    ) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ErrorResponse(HttpStatus.NOT_IMPLEMENTED.value(), "This feature is not implemented."));
    }
    
}
