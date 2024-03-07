
#include<stdio.h>
#include<fstream>
#define MAX_SIZE 420000
int main() {
	int nr, n1, n2, ma[100][100], mi[100][100], ind = 1, optiune = 4, zero = 1, n = 0, egal = 1, nac, ct = 0;
	std::ifstream f("in.txt");
	f >> nr;
	for (int i = 1; i <= nr; i++)
		for (int j = 1; j <= nr; j++)
			ma[i][j] = 0;
	for (int i = 1; i <= nr; i++)
		for (int j = 1; j <= nr; j++)
			mi[i][j] = 0;
	while (f >> n1 >> n2) {
		ma[n1][n2] = ma[n2][n1] = 1;
		mi[n1][ind] = mi[n2][ind] = 1;
		ind++;
	}
	while (optiune) {
		printf("(0-iesire, 1-matr adiac, 2-lista adiac, 3-lista incid 4-noduri izolate, 5-gr reg Introduceti optiunea dorita: ");
		scanf_s("%d", &optiune);
		if (optiune == 1)
			for (int i = 1; i <= nr; i++) {
				for (int j = 1; j <= nr; j++)
					printf("%d ", ma[i][j]);
				printf("\n");
			}
		if (optiune == 2)
			for (int i = 1; i <= nr; i++) {
				printf("%d: ", i);
				for (int j = 1; j <= nr; j++)
					if (ma[i][j] == 1)
						printf("%d, ", j);
				printf("\n");
			}
		if (optiune == 3)
			for (int i = 1; i <= ind - 1; i++) {
				for (int j = 1; j <= nr; j++)
					printf("%d ", mi[i][j]);
				printf("\n");
			}
		if (optiune == 4)
			for (int i = 1; i <= ind - 1; i++) {
				zero = 1;
				for (int j = 1; j <= nr; j++)
					if (mi[i][j] == 1) {
						zero = 0;
					}
				if (zero)
					printf("%d ", i);
				printf("\n");
			}
		if (optiune == 5) {
			for (int j = 1; j <= nr; j++)
				if (ma[1][j] == 1)
					n++;
			for (int i = 2; i <= ind - 1; i++) {
				nac = 0;
				for (int j = 1; j <= nr; j++) {
					if (mi[i][j] == 1) {
						nac++;
					}
					if (nac != 0)
						egal = 0;
				}
			}
			if (egal)
				printf("Graful este regulat.");
			else
				printf("Graful nu este regulat.");
			printf("\n");
		}
		if (optiune == 7) {
			for (int i = 1; i <= ind - 1; i++) {
				zero = 1;
				for (int j = 1; j <= nr; j++)
					if (mi[i][j] == 1) {
						zero = 0;
					}
				if (zero)
					ct++;
			}
			if (ct)
				printf("Graful nu este conex.");
			else
				printf("Graful este conex.");
			printf("\n");

		}
	}
	return 0;
}