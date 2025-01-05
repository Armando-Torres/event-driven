export interface Listener {
    run(): Promise<void>;
}