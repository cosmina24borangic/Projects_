#include<iostream>
using namespace std;
void binar_nr(int n) {
    int i, binar[33], lungime;
    for(i = 0; i <= 30; i++)
        binar[i] = 0;
    for (i = 1; n > 0; i++)
    {
        binar[i] = n % 2;
        n /= 2;
    }
    lungime = i - 1;
    for (i = lungime; i > 0; i--)
        cout << binar[i];
}
int main() {
    int  n;
    cin >> n;
    for (int j = 1; j <= n; j++) {
        binar_nr(j);
        cout << endl;
    }
        
   ;
    
    

}