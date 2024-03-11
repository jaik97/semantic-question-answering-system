# Semantic Question Answering System

## Overview

This Java project implements a prototype Question Answering system using semantic information extracted from a Distributional Semantic Model (DSM) file. The system can identify semantic relationships between words and perform tasks such as finding semantically close words and logical analogies.

## Features

- **Word Embedding and GloVe:** Utilizes GloVe (Global Vectors for Word Representation) method for word embedding, representing word meaning using high-dimensional vectors based on contextual information.

- **DSM File:** Uses "glove.6B.50d_Reduced.csv" DSM file containing word vectors. Each line in the file represents a unique word followed by its 50-dimensional vector representation.

- **Vector Operations:** Implements various vector operations in the Vector class, such as addition, subtraction, dot product, and cosine similarity.

- **Heap Sort:** Implements HeapSort algorithm for sorting Cosine Similarity values, efficiently sorting Cosine Similarity pairs to determine semantic closeness between words.

- **Semantic Operations:** Performs semantic operations like finding semantically close words and logical analogies. Calculates Cosine Similarity values between words and vectors to determine semantic relationships.

## Technologies Used

- Java programming language for implementation.
- GloVe method for word embedding.
- Distributional Semantic Models (DSM) for representing word semantics.
- BufferedReader for reading data from files.
- HeapSort algorithm for efficient sorting.
- Object-oriented programming concepts for class design and implementation.

## Dependencies

- **JUnit:** Used for unit testing.
- **Maven:** Dependency management and build automation.

## Getting Started

To get started with the project, follow these steps:

1. Clone the repository to your local machine.
2. Import the project into IntelliJ.
3. Ensure Maven dependencies are resolved by running `mvn clean install`.
4. Run the project using IntelliJ or Maven commands.

## Usage

- Modify the project files to implement additional features or customize existing functionality.
- Refer to the provided documentation and comments within the code for detailed information on each class and method.
- Follow the guidelines mentioned in the assignment document for adherence to rules and requirements.

## Contributors

- Jainendra Kumar Jain (https://github.com/jaik97)

## License

This project is licensed under the [MIT License](LICENSE).
