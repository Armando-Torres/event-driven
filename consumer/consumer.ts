import { KafkaListenerImpl } from "./src/listeners/KafkaListenerImpl";

const socketServer = new WebSocket(`ws://${process.env.WS_SERVER_HOST}:${process.env.WS_SERVER_PORT}`);
const kafkaListener = new KafkaListenerImpl(socketServer);

kafkaListener.run().catch(console.error);