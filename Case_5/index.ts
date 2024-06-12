// index.ts
import { TaskList } from './TaskList';
import * as readlineSync from 'readline-sync';

const taskList = new TaskList();
taskList.loadTasks();

const showMenu = () => {
    console.log(`
1. Add Task
2. Remove Task
3. List Tasks
4. List Completed Tasks
5. Mark Task as Completed
6. Save and Exit
`);
};

const main = () => {
    let exit = false;
    while (!exit) {
        showMenu();
        const choice = readlineSync.question('Choose an option: ');
        switch (choice) {
            case '1':
                const description = readlineSync.question('Enter task description: ');
                taskList.addTask(description);
                console.log('Task added.');
                break;
            case '2':
                const idToRemove = readlineSync.questionInt('Enter task ID to remove: ');
                taskList.removeTask(idToRemove);
                console.log('Task removed.');
                break;
            case '3':
                console.log('Tasks:');
                taskList.getTasks().forEach(task => {
                    console.log(`${task.id}. ${task.description} [${task.completed ? 'Completed' : 'Not Completed'}]`);
                });
                break;
            case '4':
                console.log('Completed Tasks:');
                taskList.getCompletedTasks().forEach(task => {
                    console.log(`${task.id}. ${task.description}`);
                });
                break;
            case '5':
                const idToComplete = readlineSync.questionInt('Enter task ID to mark as completed: ');
                taskList.markTaskAsCompleted(idToComplete);
                console.log('Task marked as completed.');
                break;
            case '6':
                taskList.saveTasks();
                exit = true;
                console.log('Tasks saved. Goodbye!');
                break;
            default:
                console.log('Invalid choice. Please try again.');
        }
    }
};

main();
