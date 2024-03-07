#include <iostream>
#include <vector>

using namespace std;



int main() {
    int a[100], b[100], n;
    cin >> n;
    for (int i = 1; i <= n; i++)
        cin >> a[i];
    for (int j = 1; j <= n; j++)
        cin >> b[j];
    int product = 0;
    for (int i = 1; i<=n; i++)
            product = product + (a[i]) * (b[i]);
    cout << product << endl;
    return 0;
}