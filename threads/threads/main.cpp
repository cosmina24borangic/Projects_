#include <iostream>
#include <fstream>
#include <chrono>
#include <thread>
#include <cmath>
#include <vector>

using namespace std;

double** allocateMatrix(int rows, int cols) {
    double** matrix = new double* [rows];
    for (int i = 0; i < rows; ++i) {
        matrix[i] = new double[cols];
    }
    return matrix;
}

void deallocateMatrix(double** matrix, int rows) {
    for (int i = 0; i < rows; ++i) {
        delete[] matrix[i];
    }
    delete[] matrix;
}

void convolute(int n, int m, int k, double** f, double** c)
{

    vector<vector<double>> temp(n, vector<double>(m, 0.0));

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            for (int ind1 = 0; ind1 < k; ind1++) {
                for (int ind2 = 0; ind2 < k; ind2++) {
                    if ((i - (k / 2) + ind1) < 0 || j - (k / 2) + ind2 < 0 || i - (k / 2) + ind1 >= n || j - (k / 2) + ind2 >= m) {
                        temp[i][j] += c[ind1][ind2] * 0;
                    }
                    else {
                        temp[i][j] += c[ind1][ind2] * f[i - (k / 2) + ind1][j - (k / 2) + ind2];
                    }
                }
            }
        }
    }

    
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            f[i][j] = temp[i][j];
        }
    }
}

void lineConv(int linie, int m, int k, double** f, double** c)
{
    vector<double> temp(m, 0.0);

    for (int j = 0; j < m; j++) {
        for (int ind1 = 0; ind1 < k; ind1++) {
            for (int ind2 = 0; ind2 < k; ind2++) {
                if ((linie - (k / 2) + ind1) < 0 || j - (k / 2) + ind2 < 0 || linie - (k / 2) + ind1 >= m || j - (k / 2) + ind2 >= m) {
                    temp[j] += c[ind1][ind2] * 0;
                }
                else {
                    temp[j] += c[ind1][ind2] * f[linie - (k / 2) + ind1][j - (k / 2) + ind2];
                }
            }
        }
    }

    
    for (int j = 0; j < m; j++) {
        f[linie][j] = temp[j];
    }
}

void lineConv2(int start, int end, int m, int k, double** f, double** c)
{
    for (int i = start; i < end; i++) {
        lineConv(i, m, k, f, c);
    }
}

void colConv(int col, int n, int k, double** f, double** c)
{
    vector<double> temp(n, 0.0);

    for (int i = 0; i < n; i++) {
        for (int ind2 = 0; ind2 < k; ind2++) {
            for (int ind1 = 0; ind1 < k; ind1++) {
                if ((i - (k / 2) + ind1) < 0 || (col - (k / 2) + ind2) < 0 || i - (k / 2) + ind1 >= n || col - (k / 2) + ind2 >= col) {
                    temp[i] += c[ind1][ind2] * 0;
                }
                else {
                    temp[i] += c[ind1][ind2] * f[i - (k / 2) + ind1][col - (k / 2) + ind2];
                }
            }
        }
    }

    // Copy the result back to the original matrix f
    for (int i = 0; i < n; i++) {
        f[i][col] = temp[i];
    }
}

int main()
{
    ifstream input("date.txt");
    ofstream output("output.txt");

    int n, m, k=3;
    input >> n >> m;

    double** f = allocateMatrix(n, m);
    double** c = allocateMatrix(k, k);

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            f[i][j] = i;
        }
    }

    for (int i = 0; i < k; i++) {
        for (int j = 0; j < k; j++) {
            if (i % 2 == 0 && j % 2 == 0) {
                c[i][j] = 1;
            }
            else {
                c[i][j] = 0;
            }
        }
    }
    
    auto time1 = chrono::steady_clock::now();

    convolute(n, m, k, f, c);

    auto time2 = chrono::steady_clock::now();
    auto diff = time2 - time1;

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            output << f[i][j] << " ";
        }
        output << endl;
    }
    output << endl;

    cout << "Static = " << chrono::duration <double, milli>(diff).count() << "ms" << endl;
    
    time1 = chrono::steady_clock::now();
    const int p = 8;
    thread t[p];
    for (int i = 0; i < p; i++) {
        int start = i * (n / p);
        int end = (i + 1) * (n / p);
        t[i] = thread(lineConv2, start, end, m, k, f, c);
    }

    for (int i = 0; i < p; i++) {
        t[i].join();
    }

     time2 = chrono::steady_clock::now();
     diff = time2 - time1;

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            output << f[i][j] << " ";
        }
        output << endl;
    }

    cout << endl;
    cout << p << ": THREADS = " << chrono::duration <double, milli>(diff).count() << "ms" << endl;

    input.close();
    output.close();

    deallocateMatrix(f, n);
    deallocateMatrix(c, k);

    return 0;
}
