# Multithreaded Calculator

The Multithreaded Calculator is a Java program that allows users to perform slow calculations on multiple threads concurrently. It provides a command-line interface for users to interact with the program and execute various commands related to starting, canceling, and managing calculations.

## Features

- Start calculations with specific inputs on separate threads
- Cancel ongoing calculations
- Check the status of calculations (running, finished, or cancelled)
- Retrieve the results of completed calculations
- Schedule calculations to start after the completion of other calculations
- Wait for all calculations to finish
- Abort all running and scheduled calculations

## Getting Started

### Prerequisites

To run the Multithreaded Calculator, you need to have the following:

- Java Development Kit (JDK) 8 or above
- Command-line interface or terminal

### Installation

1. Clone the repository to your local machine using the following command:
   ```
   git clone https://github.com/your-username/multithreaded-calculator.git
   ```

2. Navigate to the project directory:
   ```
   cd multithreaded-calculator
   ```

3. Compile the Java files using the following command:
   ```
   javac *.java
   ```

## Usage

To use the Multithreaded Calculator, follow these steps:

1. Open a command-line interface or terminal.

2. Navigate to the project directory.

3. Run the program using the following command:
   ```
   java Solution
   ```

4. The program will start and display a prompt, waiting for your commands.

5. Enter commands to interact with the program. The available commands are:
   - `start N`: Start a new calculation with input N on a new thread.
   - `cancel N`: Cancel the calculation with input N if it is currently running.
   - `running`: Display the number of calculations currently running and their inputs.
   - `get N`: Retrieve the result of the calculation for input N if it is finished.
   - `after N M`: Schedule the calculation for input M to start after the calculation for input N finishes or is cancelled.
   - `finish`: Wait for all previously requested calculations to finish.
   - `abort`: Immediately stop all running calculations and discard any scheduled calculations.

6. The program will execute the commands and provide appropriate output or messages.

7. To exit the program, you can use the `finish` command to wait for all calculations to complete, or the `abort` command to stop all calculations and exit immediately.

## Example

Here's an example of how to use the Multithreaded Calculator:

```
$ java Solution
> start 10456060
started 10456060
> running
1 calculations running: 10456060
> get 10456060
calculating
> cancel 10456060
cancelled 10456060
> running
no calculations running
> finish
finished
```

In this example, we start a calculation with input 10456060, check the status of running calculations, attempt to retrieve the result of the calculation, cancel the calculation, and then finish all calculations.
