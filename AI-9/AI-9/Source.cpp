#include <iostream>
using namespace std;
int main() {
	int m[10][10], p1, p2, q1, q2, n, n1, s=0;
	cin >> n;
	cin >> n1;
	cin >> p1;
	cin >> p2;
	cin >> q1;
	cin >> q2;
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n1; j++)
			cin >> m[i][j];
	}
	for (int i = p1; i <= q1; i++) {
		for (int j = p2; j <= q2; j++)
			s=s+m[i][j];
	}
	cout << s;


}