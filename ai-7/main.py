import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from sklearn import linear_model
from sklearn.datasets import make_regression
from sklearn.metrics import mean_squared_error
import random
from statistics import mean


class LinearRegressionModel:
    def __init__(self):
        self.intercept_ = 0.0
        self.coefficients_ = []

    def fit(self, X, y, learning_rate=0.001, num_epochs=1000):
        self.coefficients_ = [random.random() for _ in range(len(X[0]) + 1)]

        for epoch in range(num_epochs):
            errors = []

            for i in range(len(X)):
                y_predicted = self._predict(X[i])
                errors.append(y_predicted - y[i])

            error = mean(errors)

            for i in range(len(X)):
                for j in range(len(X[0])):
                    self.coefficients_[j] = self.coefficients_[j] - learning_rate * error * X[i][j]
                self.coefficients_[-1] = self.coefficients_[-1] - learning_rate * error

        self.intercept_ = self.coefficients_[-1]
        self.coefficients_ = self.coefficients_[:-1]

    def _predict(self, x):
        y = self.intercept_

        for j in range(len(x)):
            y += self.coefficients_[j] * x[j]

        return y

    def predict(self, X):
        y_predicted = [self._predict(x) for x in X]
        return y_predicted


class StochasticGradientDescentRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coefficients_ = []

    def fit(self, X, y, learning_rate=0.001, num_epochs=1000):
        self.coefficients_ = [random.random() for _ in range(len(X[0]) + 1)]

        for epoch in range(num_epochs):
            for i in range(len(X)):
                y_predicted = self._predict(X[i])
                error = y_predicted - y[i]

                for j in range(len(X[0])):
                    self.coefficients_[j] = self.coefficients_[j] - learning_rate * error * X[i][j]
                self.coefficients_[-1] = self.coefficients_[-1] - learning_rate * error

        self.intercept_ = self.coefficients_[-1]
        self.coefficients_ = self.coefficients_[:-1]

    def _predict(self, x):
        y = self.intercept_

        for j in range(len(x)):
            y += self.coefficients_[j] * x[j]

        return y

    def predict(self, X):
        y_predicted = [self._predict(x) for x in X]
        return y_predicted


def load_data(filename, input_features, output_feature):
    data = pd.read_csv(filename)
    features = []

    for feature in input_features:
        features.append([float(value) for value in data[feature]])

    output = [float(value) for value in data[output_feature]]
    return features, output


def plot_histogram(data, variable_name):
    plt.hist(data, 10)
    plt.title('Histogram of ' + variable_name)
    plt.show()


def plot_linearity(input_feature, output_feature, input_name, output_name):
    plt.plot(input_feature, output_feature, 'ro')
    plt.xlabel(input_name)
    plt.ylabel(output_name)
    plt.title(input_name + ' vs. ' + output_name)
    plt.show()


def train_and_test(features, output):
    np.random.seed(0)
    random.seed(0)

    num_samples = len(features[0])
    indices = np.random.permutation(num_samples)
    num_training_samples = int(num_samples * 0.8)
    training_idx, test_idx = indices[:num_training_samples], indices[num_training_samples:]

    training_features = np.array([[features[j][i] for j in range(len(features))] for i in training_idx])
    training_output = np.array([output[i] for i in training_idx])
    test_features = np.array([[features[j][i] for j in range(len(features))] for i in test_idx])
    test_output = np.array([output[i] for i in test_idx])

    # Linear regression using custom implementation
    model = LinearRegressionModel()
    model.fit(training_features, training_output)
    custom_predictions = model.predict(test_features)

    # Linear regression using scikit-learn
    reg = linear_model.LinearRegression()
    reg.fit(training_features, training_output)
    sklearn_predictions = reg.predict(test_features)

    # Stochastic gradient descent regression using custom implementation
    sgd_model = StochasticGradientDescentRegression()
    sgd_model.fit(training_features, training_output)
    sgd_custom_predictions = sgd_model.predict(test_features)

    mse_custom = mean_squared_error(test_output, custom_predictions)
    mse_sklearn = mean_squared_error(test_output, sklearn_predictions)
    mse_sgd_custom = mean_squared_error(test_output, sgd_custom_predictions)

    print("MSE (Custom Linear Regression):", mse_custom)
    print("MSE (Scikit-learn Linear Regression):", mse_sklearn)
    print("MSE (Custom Stochastic Gradient Descent):", mse_sgd_custom)

    # Plotting
    plot_histogram(test_output, 'Actual')
    plot_histogram(custom_predictions, 'Custom Linear Regression')
    plot_histogram(sklearn_predictions, 'Scikit-learn Linear Regression')
    plot_histogram(sgd_custom_predictions, 'Custom Stochastic Gradient Descent')
    plot_linearity(test_output, custom_predictions, 'Actual', 'Custom Linear Regression')
    plot_linearity(test_output, sklearn_predictions, 'Actual', 'Scikit-learn Linear Regression')
    plot_linearity(test_output, sgd_custom_predictions, 'Actual', 'Custom Stochastic Gradient Descent')


if __name__ == '__main__':
    filename = 'data.csv'
    input_features = ['Feature1', 'Feature2', 'Feature3']
    output_feature = 'Output'
    features, output = load_data(filename, input_features, output_feature)
    train_and_test(features, output)
