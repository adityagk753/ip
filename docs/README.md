# Cody User Guide

ip/docs/Ui.png.png

## Tasks
You can use Cody to keep track of your tasks. Each task can be marked as done, or unmarked (that is, marked as not done).

There are 3 types of tasks that Cody supports.

## Adding todos
You can add todo tasks, which are tasks consisting of just a description.

Example: `todo complete homework`

Assuming that this is the only task you've added thus far, this is the output you should see.
```
Got it. I've added this task:
[T][] complete homework
Now you have 1 task(s) in the list
```

## Adding deadlines
You can add deadline tasks, which are tasks consisting of a description, and a deadline.

Example: `deadline complete homework /by 15 Aug 2025`

Expected output:
```
Got it. I've added this task:
[D][] complete homework (by: 2025-08-15)
Now you have 2 task(s) in the list
```

Note that the deadline date provided should be in DD MMM YYYY format.

## Adding events
You can add event tasks, which are tasks consisting of a description, and a start date and an end date.

Example: `event attend hackathon /from 15 Aug 2025 /to 17 Aug 2025`

Expected output:
```
Got it. I've added this task:
[E][] attend hackathon (from: 2025-08-15 to: 2025-08-17)
Now you have 3 task(s) in the list
```

Note that the start and end dates provided should be in DD MMM YYYY format.

## Listing tasks
You can list tasks to see all the current tasks, together with their respective status (whether they're completed or not completed).

Tasks appear in the order that they were inserted.

Example: `list`

Expected output:
```
1. [T][] complete homework
2. [D][] complete homework (by: 2025-08-15)
3. [E][] attend hackathon (from: 2025-08-15 to: 2025-08-17)
```

## Deleting tasks
You can also delete a task.

Example: `delete 1`

Expected output:
```
Noted! I've removed this task!
[T][] complete homework
Now you have 2 tasks in the list.
```

## Finding tasks
You can find a task by providing a substring of the description.

Example: `find homework`

Expected output:
```
Here are the matching tasks in your list.
1. [D][] complete homework (by: 2025-08-15)
```

## Detection of duplicate tasks
Say you don't remember that you've already added a task to Cody. When you try to insert the same task again, if there exists a not-completed task with the same details in Cody's memory, this task will not be added to the list!

However, suppose that the existing task (with the same matching details) has been marked as complete. When you try to add a new task with the same details as that existing task, Cody will still allow you to add that task.
