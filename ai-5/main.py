import math
import os
import pandas as pd
import matplotlib.pyplot as plt

def read_csv(file_name):
    return pd.read_csv(os.path.join(os.getcwd(), 'data', file_name))

def plot_predictions(data_frame, feature):
    y = range(len(data_frame[feature]))
    real, = plt.plot(y, data_frame[feature], 'ro', label="Real")
    computed, = plt.plot(y, data_frame["Predicted_" + feature], 'bo', label="Computed")
    plt.legend(handles=[real, computed])
    plt.show()

def calculate_regression_error_sum(real, computed):
    total_error = 0
    for r, c in zip(real, computed):
        feature_error = sum(abs(r_i - c_i) for r_i, c_i in zip(r, c))
        total_error += feature_error
    return total_error / len(real[0])

def calculate_regression_error_sqrt(real, computed):
    total_error = 0
    for r, c in zip(real, computed):
        feature_error = sum((r_i - c_i) ** 2 for r_i, c_i in zip(r, c))
        total_error += feature_error
    return math.sqrt(total_error / len(real[0]))

def evaluate_classification(real, computed, labels):
    accuracy = sum(1 if r == c else 0 for r, c in zip(real, computed)) / len(real)

    TP = {}
    FP = {}
    TN = {}
    FN = {}

    for label in labels:
        TP[label] = sum(1 if (r == label and c == label) else 0 for r, c in zip(real, computed))
        FP[label] = sum(1 if (r != label and c == label) else 0 for r, c in zip(real, computed))
        TN[label] = sum(1 if (r != label and c != label) else 0 for r, c in zip(real, computed))
        FN[label] = sum(1 if (r == label and c != label) else 0 for r, c in zip(real, computed))

    precision = {label: TP[label] / (TP[label] + FP[label]) for label in labels}
    recall = {label: TP[label] / (TP[label] + FN[label]) for label in labels}

    return accuracy, precision, recall

def calculate_regression_loss_sum(real, computed):
    total_loss = 0
    for r, c in zip(real, computed):
        feature_loss = sum(abs(r_i - c_i) for r_i, c_i in zip(r, c))
        total_loss += feature_loss
    return total_loss

def evaluate_binary_classification_loss(real, computed, positive):
    real_outputs = [[1, 0] if label == positive else [0, 1] for label in real]
    num_classes = len(set(real))
    dataset_CE = 0.0
    for i in range(len(real)):
        sample_CE = -sum(real_outputs[i][j] * math.log(computed[i][j]) for j in range(num_classes))
        dataset_CE += sample_CE
    mean_CE = dataset_CE / len(real)
    return mean_CE

def evaluate_multi_class_loss(target_values, raw_outputs):
    expected_values = [math.exp(value) for value in raw_outputs]
    sum_expected_values = sum(expected_values)
    map_outputs = [value / sum_expected_values for value in expected_values]
    sample_CE = -sum(target_values[j] * math.log(map_outputs[j]) for j in range(len(target_values)))
    return sample_CE

def evaluate_multi_label_loss(target_values, raw_outputs):
    map_outputs = [1 / (1 + math.exp(-val)) for val in raw_outputs]
    sample_CE = -sum(target_values[j] * math.log(map_outputs[j]) for j in range(len(target_values)))
    return sample_CE

# Read CSV files
sports_data = read_csv("sport.csv")
flowers_data = read_csv("flowers.csv")

# Plot sport predictions
plot_predictions(sports_data, "Weight")

# Evaluate regression errors
regression_sum_error = calculate_regression_error_sum(
    [sports_data['Weight'], sports_data['Waist'], sports_data['Pulse']],
    [sports_data['PredictedWeight'], sports_data['PredictedWaist'], sports_data['PredictedPulse']]
)
regression_sqrt_error = calculate_regression_error_sqrt(
    [sports_data['Weight'], sports_data['Waist'], sports_data['Pulse']],
    [sports_data['PredictedWeight'], sports_data['PredictedWaist'], sports_data['PredictedPulse']]
)
print('Sum Error:', regression_sum_error)
print('Sqrt Error:', regression_sqrt_error)

# Evaluate classification accuracy, precision, and recall
flower_types = list(set(flowers_data['Type']))
accuracy, precisions, recalls = evaluate_classification(
    flowers_data['Type'], flowers_data['PredictedType'], flower_types
)
print('Accuracy:', accuracy)
for flower_type in flower_types:
    print('Precision for', flower_type, 'is', precisions[flower_type])
    print('Recall for', flower_type, 'is', recalls[flower_type])

# Evaluate regression and classification losses
real_values = ['spam', 'spam', 'ham', 'ham', 'spam', 'ham']
computed_outputs = [[0.7, 0.3], [0.2, 0.8], [0.4, 0.6], [0.9, 0.1], [0.7, 0.3], [0.4, 0.6]]

regression_loss = calculate_regression_loss_sum(
    [sports_data['Weight'], sports_data['Waist'], sports_data['Pulse']],
    [sports_data['PredictedWeight'], sports_data['PredictedWaist'], sports_data['PredictedPulse']]
)
binary_classification_loss = evaluate_binary_classification_loss(real_values, computed_outputs, 'spam')
multi_class_loss = evaluate_multi_class_loss([0, 1, 0, 0, 0], [-0.5, 1.2, 0.1, 2.4, 0.3])
multi_label_loss = evaluate_multi_label_loss([0, 1, 0, 0, 1], [-0.5, 1.2, 0.1, 2.4, 0.3])

print('Regression Loss:', regression_loss)
print('Binary Classification Loss:', binary_classification_loss)
print('Multi-Class Loss:', multi_class_loss)
print('Multi-Label Loss:', multi_label_loss)
