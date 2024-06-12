export class Task {
    constructor(
        public id: number,
        public description: string,
        public completed: boolean = false
    ) {}

    markAsCompleted() {
        this.completed = true;
    }
}
