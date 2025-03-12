# AdMediation SDK

This project is aims to implement an **Ad Mediation SDK** capable of managing multiple ad networks
dynamically based on configurations fetched from a server.

### Modules

- `admediation-core`
    - Contains the core classes and serves as the SDK initializer.
    - Handles the main logic for managing ad networks and configurations.

- `admediation-cdmob`
    - Responsible for integrating the **AdMob** dependency and providing utility functions for AdMob
      integration.
    - This module has no standalone functionality and works exclusively in conjunction with
      `admediation-core`.

##### Example modules:

- `example-admob`
    - A java application that is demonstrating how to integrate and use the AdMediation SDK with
      AdMob.

- `example-tapsel`
    - [Work in progress]

### Roadmap

- [ ] Support AdMob
- [ ] Support Tapsel SDK
- [ ] Writing unit tests
- [ ] Compatible with Java projects
- [ ] Apply restriction in multi-thread environments
- [ ] Remove reflections
- [ ] ...

### Known issues

- [ ] **Runtime Exceptions:** Some runtime exceptions are causing the application to crash. These
  need to be identified and handled gracefully.
- [ ] **Non Google Services** devices is not considered yet. And may cause exception or have
  unexpected behavior.
- [ ] **Logger Organization:** The logging system requires better categorization and structure for
  improved debugging and monitoring.
- [ ] **TODO**s and **FIXME**s