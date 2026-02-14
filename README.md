# Insider QA Automation Project

This project is an automated UI test implementation for Insider’s Careers page using **Java + Selenium + TestNG** and following the **Page Object Model (POM)** design pattern.

## 📌 Project Purpose

The purpose of this project is to:

- Navigate to Insider website
- Go to Careers page
- Open Open Positions
- Filter jobs by:
  - Location: Turkey
  - Team: Quality Assurance
- Verify that:
  - All listed jobs belong to QA / Quality team
  - All jobs are located in Turkey
- Open the first job posting
- Click Apply
- Verify redirection to Lever Application Form

---

## 🛠 Technologies Used

- Java 19
- Selenium 4.23.1
- TestNG
- Maven
- WebDriverManager
- Page Object Model (POM)

---

### Base Layer
- `BaseTest` → WebDriver setup & teardown
- `BasePage` → Common Selenium utilities

### Pages Layer
Each page contains:
- Locators
- Page-specific actions
- Page-level validations

### Test Layer
Contains the test scenario using Page Objects only (no direct WebDriver usage).

---

## ✅ Test Scenarios

### 1️⃣ Main Flow Test

- Navigate to homepage
- Accept cookies
- Go to Careers
- Open jobs
- Apply filters
- Validate filtered job list
- Open first job
- Click Apply
- Verify Lever application form

### 2️⃣ Filter Validation Test

- Navigate to Jobs page
- Apply filters
- Validate all job postings belong to QA in Turkey

---

## 📸 Screenshot on Failure

If any test step fails:
- Screenshot is automatically captured
- Saved under `/screenshots` folder

---

## ▶️ How to Run Tests

### Using Maven:

```bash
mvn clean test
```
### Using IntelliJ:

Right click → Run InsiderTest
📌 Design Principles Followed
Page Object Model (POM)
Clean separation of concerns
No BDD frameworks used (as requested)
Explicit waits used for stability
Screenshot mechanism for failed steps

👤 Author
Egemen Guven



