#include<iostream>
using namespace std;
int main() {

    int m, n, v[100], k;
    cin >> n;
    cin >> k;
    for (int i = 1; i <= n; i++)
        cin >> v[i];
    m = n;
    bool sorted;
    do {
        sorted = true;
        for (int i = 1; i < m; i++)
            if (v[i] < v[i + 1]) {
                int aux = v[i];
                v[i] = v[i + 1];
                v[i + 1] = aux;
                sorted = false;
            }
        m--;
    } while (!sorted);
    //for (int i = 1; i <= n; i++)
     //   cout<<" "<< v[i];
    
    cout << v[k];
}