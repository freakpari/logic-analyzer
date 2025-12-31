# 🧠 Logic Coverage Analyzer for Software Testing course

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

A powerful full-stack tool designed for software testing.  
It visualizes boolean expressions, calculates **CoC** (Combinatorial Coverage) and **CACC** (Correlated Active Clause Coverage) metrics, and automatically generates optimized **JUnit 5** test cases.

![App Screenshot](screenshot.png)
> *(Place a screenshot of your app named `screenshot.png` in the root directory)*

---

## ✨ Key Features

- **📊 Combinatorial Coverage (CoC)**  
  Generates and visualizes the complete truth table for any boolean expression.

- **🎯 Correlated Active Clause Coverage (CACC)**  
  Uses advanced algorithms to identify the *minimal* set of test cases required to prove clause determination.

- **☕ Auto-Generated JUnit 5 Code**  
  Instantly converts logic tables into ready-to-run Java test classes.  
  The generated `.java` file can be downloaded directly.

- **⚡ Custom Logic Parser**  
  A custom-built recursive descent parser (backend) that supports nested parentheses and logical operators without external evaluation libraries.

- **🎨 Modern UI**  
  Clean and responsive interface built with Bootstrap 5 and Vanilla JavaScript.

---

## 🛠 Tech Stack

- **Backend:** Java 17, Spring Boot 3 (Spring Web)
- **Frontend:** HTML5, CSS3, JavaScript (Fetch API), Bootstrap 5
- **Build Tool:** Maven
- **Architecture:** MVC (Model–View–Controller), Service-Oriented

---

## 🚀 Getting Started

### Prerequisites

- JDK 17 or higher
- Maven installed

### Installation

```bash
git clone https://github.com/YOUR_USERNAME/logic-analyzer.git
cd logic-analyzer
mvn spring-boot:run
```

Open your browser and visit:

```
http://localhost:8080
```

---

## 💡 Usage Examples

Simple:
```
a and b
```

Intermediate:
```
(a or b) and c
```

Complex:
```
a or (b and not (c or d))
```

---

## 📚 Theory: What is CACC?

Correlated Active Clause Coverage (CACC) verifies that each clause independently affects the predicate outcome.

---

## 🤝 Contributing

Contributions are welcome! Fork the repo, create a feature branch, commit, push, and open a PR.

---

## 📄 License

MIT License
