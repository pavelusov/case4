import { Task } from './Task';
import * as fs from 'fs-extra';

export class TaskList {
    private tasks: Task[] = [];
    private filePath: string = 'tasks.json';

    addTask(description: string) {
        const id = this.tasks.length > 0 ? this.tasks[this.tasks.length - 1].id + 1 : 1;
        const task = new Task(id, description);
        this.tasks.push(task);
        this.saveTasks();
    }

    removeTask(id: number) {
        this.tasks = this.tasks.filter(task => task.id !== id);
        this.saveTasks();
    }

    getTasks(): Task[] {
        return this.tasks;
    }

    getCompletedTasks(): Task[] {
        return this.tasks.filter(task => task.completed);
    }

    markTaskAsCompleted(id: number) {
        const task = this.tasks.find(task => task.id === id);
        if (task) {
            task.markAsCompleted();
            this.saveTasks();
        }
    }

    saveTasks() {
        fs.writeJsonSync(this.filePath, this.tasks);
    }

    loadTasks() {
        if (fs.existsSync(this.filePath)) {
            this.tasks = fs.readJsonSync(this.filePath);
        }
    }
}
