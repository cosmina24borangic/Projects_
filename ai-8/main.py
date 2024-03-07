import numpy as np
import matplotlib.pyplot as plt
from sklearn.preprocessing import StandardScaler
from sklearn.datasets import load_breast_cancer
from sklearn.datasets import load_iris
from sklearn import linear_model
from random import random
from math import exp


def sigmoid(x):
    return 1 / (1 + np.exp(-x))


class MyLogisticRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coefficient_ = []

    def fit(self, X, y, learning_rate=0.001, no_epochs=1000):
        self.coefficient_ = [random() for _ in range(X.shape[1] + 1)]
        for epoch in range(no_epochs):
            for i in range(X.shape[0]):
                y_computed = sigmoid(self.evaluate(X[i], self.coefficient_))
                crt_error = y_computed - y[i]
                for j in range(X.shape[1]):
                    self.coefficient_[j + 1] -= learning_rate * crt_error * X[i][j]
                self.coefficient_[0] -= learning_rate * crt_error * 1
        self.intercept_ = self.coefficient_[0]
        self.coefficient_ = self.coefficient_[1:]

    def evaluate(self, xi, coefficient):
        yi = coefficient[0]
        for j in range(len(xi)):
            yi += coefficient[j + 1] * xi[j]
        return yi

    def predict_one_sample(self, sample_features):
        threshold = 0.5
        coefficients = [self.intercept_] + [c for c in self.coefficient_]
        computed_float_value = self.evaluate(sample_features, coefficients)
        computed01_value = sigmoid(computed_float_value)
        computed_label = 0 if computed01_value < threshold else 1
        return computed_label

    def predict(self, in_test):
        computed_labels = [self.predict_one_sample(sample) for sample in in_test]
        return computed_labels


class MyLogisticRegressionMultipleLabels:
    def __init__(self):
        self.intercept_ = []
        self.coefficient_ = []

    def fit_batch(self, X, y, learning_rate=0.001, no_epochs=1000):
        self.coefficient_ = []
        self.intercept_ = []
        labels = np.unique(y)
        for label in labels:
            coefficient = [random() for _ in range(X.shape[1] + 1)]
            for _ in range(no_epochs):
                errors = [0] * len(coefficient)
                for input, output in zip(X, y):
                    y_computed = sigmoid(self.evaluate(input, coefficient))
                    error = y_computed - 1 if output == label else y_computed
                    for i, xi in enumerate([1] + list(input)):
                        errors[i] += error * xi
                for i in range(len(coefficient)):
                    coefficient[i] -= learning_rate * errors[i]
            self.intercept_.append(coefficient[0])
            self.coefficient_.append(coefficient[1:])

    def fit(self, X, y, learning_rate=0.001, no_epochs=1000):
        self.intercept_ = []
        self.coefficient_ = []
        labels = np.unique(y)
        for label in labels:
            coefficient = [random() for _ in range(X.shape[1] + 1)]
            for _ in range(no_epochs):
                for input, output in zip(X, y):
                    y_computed = sigmoid(self.evaluate(input, coefficient))
                    error = y_computed - 1 if output == label else y_computed
                    for i, xi in enumerate([1] + list(input)):
                        coefficient[i] -= learning_rate * error * xi
            self.intercept_.append(coefficient[0])
            self.coefficient_.append(coefficient[1:])

    def evaluate(self, xi, coefficient):
        yi = coefficient[0]
        for j in range(len(xi)):
            yi += coefficient[j + 1] * xi[j]
        return yi

    def predict_one_sample(self, sample_features):
        probabilities = []
        for label, intercept, coefficients in zip(self.labels, self.intercept_, self.coefficient_):
            coefficients = [intercept] + coefficients
            computed_float_value = self.evaluate(sample_features, coefficients)
            computed01_value = sigmoid(computed_float_value)
            probabilities.append(computed01_value)
        max_prob = max(probabilities)
        predicted_labels = [1 if prob == max_prob else 0 for prob in probabilities]
        return predicted_labels

    def predict(self, in_test):
        computed_labels = [self.predict_one_sample(sample) for sample in in_test]
        return computed_labels


# Testing the logistic regression models

# Load dataset
iris_data = load_iris()
X = iris_data.data
y = iris_data.target

# Feature scaling
scaler = StandardScaler()
X = scaler.fit_transform(X)

# Logistic Regression - Single Label
single_label_model = linear_model.LogisticRegression()
single_label_model.fit(X, y)

# MyLogisticRegression - Single Label
my_single_label_model = MyLogisticRegression()
my_single_label_model.fit(X, y)

# Logistic Regression - Multiple Labels
multiple_labels_model = linear_model.LogisticRegression(multi_class='multinomial', solver='lbfgs')
multiple_labels_model.fit(X, y)

# MyLogisticRegression - Multiple Labels
my_multiple_labels_model = MyLogisticRegressionMultipleLabels()
my_multiple_labels_model.fit(X, y)

# Random sample for prediction
random_sample = np.array([5.1, 3.5, 1.4, 0.2]).reshape(1, -1)

# Predictions
single_label_prediction = single_label_model.predict(random_sample)
my_single_label_prediction = my_single_label_model.predict(random_sample)
multiple_labels_prediction = multiple_labels_model.predict(random_sample)
my_multiple_labels_prediction = my_multiple_labels_model.predict(random_sample)

# Print predictions
print("Single Label Prediction (Logistic Regression):", single_label_prediction)
print("Single Label Prediction (MyLogisticRegression):", my_single_label_prediction)
print("Multiple Labels Prediction (Logistic Regression):", multiple_labels_prediction)
print("Multiple Labels Prediction (MyLogisticRegression):", my_multiple_labels_prediction)

# Plotting decision boundaries
h = 0.02
x_min, x_max = X[:, 0].min() - 1, X[:, 0].max() + 1
y_min, y_max = X[:, 1].min() - 1, X[:, 1].max() + 1
xx, yy = np.meshgrid(np.arange(x_min, x_max, h), np.arange(y_min, y_max, h))
Z = multiple_labels_model.predict(np.c_[xx.ravel(), yy.ravel()])
Z = Z.reshape(xx.shape)
plt.figure(figsize=(10, 6))
plt.contourf(xx, yy, Z, cmap=plt.cm.RdYlBu, alpha=0.8)
plt.scatter(X[:, 0], X[:, 1], c=y, cmap=plt.cm.Set1)
plt.xlabel("Feature 1")
plt.ylabel("Feature 2")
plt.title("Logistic Regression - Multiple Labels")
plt.show()
