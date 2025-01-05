import { Kafka, type Consumer } from "kafkajs";
import type { Listener } from "./Listener";
import { OrderLine } from "../order/OrderLine";
import { Order } from "../order/Order";

export class KafkaListenerImpl implements Listener {
    private readonly BROKER: string = `${process.env.KAFKA_BROKER_HOST}:${process.env.KAFKA_BROKER_PORT}`;
    private readonly TOPIC: string = `${process.env.KAFKA_TOPIC}`;
    
    private listenerInstance: Consumer;
    private webSocket: WebSocket;

    constructor(socket: WebSocket) {
        this.listenerInstance =  (new Kafka({
            clientId: 'kafka-listener',
            brokers: [this.BROKER]
        })).consumer({ 
            groupId: 'web-consumer' 
        });

        this.webSocket = socket;
    }

    async run(): Promise<void> {
        await this.listenerInstance.connect();
        await this.listenerInstance.subscribe({ topic: this.TOPIC, fromBeginning: true })

        await this.listenerInstance.run({
            eachMessage: async ({ topic, partition, message }) => {
                // @ts-ignore
                const payload = JSON.parse(message.value?.toString());
                // @ts-ignore

                if (payload.eventType == "food-order") {
                    const lines: Set<OrderLine> = this.createLines(payload.data.items);
                    const address: string = this.createAddress(payload.data.address);
                    const order: Order = new Order(payload.data.id, address, lines);
    
                    this.listenerInstance.logger().info(`Order payload: ${order.toJSON()}`)
    
                    this.webSocket.send(order.toJSON());
                }
            },
        })
    }

    private createLines(rawLines: Set<Object>): Set<OrderLine> {
        let lines: Set<OrderLine> = new Set();
        for (const line of rawLines) {
            // @ts-ignore
            lines.add(new OrderLine(line.desc, line.quantity))
        }

        return lines;
    }

    private createAddress(rawAddress: Object): string {
        // @ts-ignore
        return `${rawAddress.address} - ${rawAddress.details}`;
    }
}