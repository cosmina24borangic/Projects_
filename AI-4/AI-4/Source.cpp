#include <iostream>
#include <string.h>
using namespace std;

int main() {

    char a[201], cuvant[51], listaCuvinte[50][51], * p;
    cin.get(a, 200);
    cin.get();
    cin >> cuvant;
    int nrEl = 0;
    p = strtok(a, " ");
    while (p) {

        strcpy(listaCuvinte[nrEl++], p);
        p = strtok(NULL, " ");

    }

    int i, nrAparitii = 0;
    for (i = 0; i < nrEl; i++) {

        if (strcmp(listaCuvinte[i], cuvant) == 0)
            nrAparitii++;

    }

    cout << "Cuvantul citit apare in text de " << nrAparitii << " ori\n";

    return 0;
}