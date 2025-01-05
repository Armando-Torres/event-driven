import type { OrderLine } from "./OrderLine";

export class Order {
    private id: number;
    private address: string;
    private lines: Set<OrderLine>

    constructor(id: number, address:string, lines: Set<OrderLine>) {
        this.id = id;
        this.address = address;
        this.lines = lines;
    }

    toJSON(): string {
        return JSON.stringify({
            id: this.id,
            address: this.address,
            lines: Array.from(this.lines, (line) => ({name: line.name, quantity: line.qty}))
        })
    }
}