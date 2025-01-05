export class OrderLine {
    private productName: string;
    private quantity: number;

    constructor(name: string, qty: number) {
        this.productName = name;
        this.quantity = qty;
    }

    get name(): string {
        return this.productName;
    }

    get qty(): number {
        return this.quantity;
    }
}