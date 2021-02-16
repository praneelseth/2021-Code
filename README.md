# FRC 6901 2021 Codebase

This is the code base for FRC Team 6901 Knights Robotics.

## Contribution Guidelines

### Building Robot Code

To help ensure that any changes you make to the code are viable and match format standards, please build your code.

Here are the summarized steps:
1. Right click the build.gradle file
2. Press Build code

Do note that we are using the spotless formatter to format our code. If your build fails because of this, run: `./gradlew spotlessApply` in a terminal pointed to the robot project directory to fix this.

### Using Github

[Here](https://youtu.be/VHbUQaJ2TIo) is a video explaining how to contribute to this repository.

Here are the summarized steps:
1. [Fork](https://github.com/frc-6901/2021-Code/fork) this repository through Github

2. Clone the fork onto your computer

3. Setup the upstream: `git remote add upstream https://github.com/frc-6901/2021-Code.git`

4. Create a branch for your change with `git checkout -b feature`

5. Make your changes and add them: `git add .`

6. Commit your changes: `git commit -m "Change description"`

7. Push to origin `git push origin feature`

8. Open a pull request on Github