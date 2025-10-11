FROM eclipse-temurin:21-alpine AS build

WORKDIR /app

# Copy Maven wrapper and make it executable
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw

# Copy pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B

# Copy source code and build
COPY . .
RUN ./mvnw clean package -DskipTests

# Runtime stage - AWS Lambda Java 21
FROM public.ecr.aws/lambda/java:21

# Copy the shaded JAR to Lambda task root
COPY --from=build /app/target/aproil-backend-0.0.1-SNAPSHOT.jar ${LAMBDA_TASK_ROOT}/lib/

# Set the Lambda handler
CMD ["pe.noltek.aproil.backend.lambda.StreamLambdaHandler::handleRequest"]