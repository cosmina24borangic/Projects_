#include <iostream>
using namespace std;
int main() {
	int a[100], n, b[100], c = 0;
	cin >> n;
	for (int j = 1; j <= n; j++)
		b[j]=0;
	for (int j = 1; j <= n; j++)
		cin >> a[j];
	for (int k = 1; k <= n; k++)
		b[a[k]]++;
	for (int i = 0; i <= 100; i++)
		if (b[i] > 1)
			c = i;
	cout << c;
}