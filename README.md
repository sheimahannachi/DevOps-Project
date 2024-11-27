# 🚀 **CI/CD Pipeline Overview**

This project integrates a fully automated **CI/CD pipeline** designed to ensure efficient building, testing, security scanning, and deployment.

Here’s an overview of the key stages and the additional monitoring tools implemented:

---

## 🛠 **What I Did in the Pipeline**

### 1. **Checkout** 🧑‍💻  
The pipeline retrieves the latest version of the source code from **GitHub** to ensure it works with the most up-to-date codebase.

### 2. **Maven Clean and Build** 🔨  
The project is cleaned to remove previous build artifacts, then compiled to produce executable binaries.

### 3. **JUnit and Mockito Tests** 🧪  
Unit tests are executed using **JUnit** and **Mockito** to ensure the core functionality of the application is correct.

### 4. **SonarQube Analysis with JaCoCo** 📊  
Static code analysis is performed using **SonarQube** to detect code quality issues, vulnerabilities, and code smells. **JaCoCo** is integrated to measure test coverage, ensuring the tests adequately cover the application’s code.

### 5. **Create Package** 📦  
The compiled code is packaged into an artifact (e.g., a **JAR** or **WAR**) suitable for deployment.

### 6. **Deploy to Nexus** 🚀  
The artifact is uploaded to **Nexus** for version control and artifact storage, making it easily accessible for deployment or further use.

### 7. **Build Docker Image** 🐳  
A **Docker** image is built from the packaged application to prepare it for containerized deployment.

### 8. **Trivy Scan** 🔒  
The Docker image is scanned using **Trivy** to identify any known vulnerabilities, ensuring that the image is secure before deployment.

### 9. **Push Docker Image to DockerHub** 🌐  
The Docker image is pushed to **DockerHub**, allowing it to be pulled and deployed in various environments.

### 10. **Deploy with Docker Compose** 🔧  
The application is deployed using **Docker Compose**, orchestrating multiple containers to run seamlessly together.

### 11. **Post Actions** 📧  
After the pipeline execution, an automated email is sent, containing:
- The **Trivy** vulnerability scan report.
- A summary of the pipeline’s success or failure for each stage.

---

## 📈 **Additional Integrations**

### **Prometheus and Grafana**  
These tools are integrated to monitor the application’s performance and visualize metrics, providing real-time insights into the health and stability of the system.

---

This automated pipeline ensures high-quality code, secure builds, and streamlined deployments, while providing robust monitoring and feedback through **SonarQube**, **Prometheus**, and **Grafana**. The pipeline also leverages **Docker** for containerized deployments, **Trivy** for vulnerability scanning, and **Nexus** for artifact management, ensuring a fully integrated, secure, and scalable CI/CD workflow. This combination of tools enables efficient development, testing, and deployment processes while maintaining high standards of security and performance monitoring.


---

![Capture d’écran 2024-11-27 114818](https://github.com/user-attachments/assets/3ed0ca31-af90-4e8a-96a5-3f06da5c91cc)

