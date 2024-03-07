import networkx as nx
import matplotlib.pyplot as plt

def greedy_algorithm(G):
    # Inițializăm fiecare nod ca o comunitate separată
    communities = {node: node for node in G.nodes()}
    m = G.number_of_edges()
    Q = 0

    while True:
        max_delta_q = 0
        max_node = None
        max_community = None

        # Iterăm prin fiecare nod din rețea
        for node in G.nodes():
            curr_community = communities[node]
            curr_q = compute_modularity(G, communities, m)

            # Iterăm prin fiecare vecin al nodului
            for neighbor in G.neighbors(node):
                # Mutăm nodul în comunitatea vecinului său și calculăm creșterea potențială a modularității
                new_community = communities[neighbor]
                if new_community == curr_community:
                    continue
                communities[node] = new_community
                delta_q = compute_delta_modularity(G, communities, m, node, curr_community, new_community)

                # Păstrăm mutarea cu cea mai mare creștere a modularității
                if delta_q > max_delta_q:
                    max_delta_q = delta_q
                    max_node = node
                    max_community = new_community

            # Restabilim comunitatea inițială a nodului
            communities[node] = curr_community

        # Dacă nu se mai poate realiza nicio mutare care să crească modularitatea, ieșim din bucla while
        if max_delta_q == 0:
            break

        # Mutăm nodul în comunitatea vecinului său care oferă cea mai mare creștere a modularității
        communities[max_node] = max_community
        Q += max_delta_q

    return communities, Q


def compute_modularity(G, communities, m):
    Q = 0
    for i, j in G.edges():
        if communities[i] == communities[j]:
            Q += 1 - G[i][j]['weight'] / (2 * m)
        else:
            Q -= G[i][j]['weight'] / (2 * m)
    return Q


def compute_delta_modularity(G, communities, m, node, curr_community, new_community):
    delta_q = 0
    for neighbor in G.neighbors(node):
        if communities[neighbor] == curr_community:
            delta_q -= G[node][neighbor]['weight'] / m
        elif communities[neighbor] == new_community:
            delta_q += G[node][neighbor]['weight'] / m
    return delta_q


if __name__ == '__main__':
    '''
    #karate
    G = nx.DiGraph()

    # adăugăm nodurile la graful nostru
    nodes = range(1, 35)
    G.add_nodes_from(nodes)

    # adăugăm muchiile la graful nostru
    edges = [(1, 1), (2, 1), (3, 1), (4, 1), (5, 1), (6, 1), (7, 1), (8, 1), (9, 2), (10, 2), (11, 1),
             (12, 1), (13, 1), (14, 1), (15, 2), (16, 2), (17, 1), (18, 1), (19, 2), (20, 1), (21, 2), (22, 1),
             (23, 2), (24, 2), (25, 2), (26, 2), (27, 2), (28, 2), (29, 2), (30, 2), (31, 2), (32, 2),(33, 2), (34, 2)]

    G.add_edges_from(edges)
    '''
    '''
    #football
    G = nx.DiGraph()

    # adaugă muchiile și nodurile
    edges = [(1, 8), (2, 1), (3, 3), (4, 4), (5, 8), (6, 4), (7, 3), (8, 9), (9, 9), (10, 8), (11, 4), (12, 11),
             (13, 7), (14, 3), (15, 7), (16, 3), (17, 8), (18, 10), (19, 7), (20, 2), (21, 10), (22, 9), (23, 9),
             (24, 8), (25, 11), (26, 1), (27, 7), (28, 10), (29, 12), (30, 2), (31, 2), (32, 7), (33, 3), (34, 1),
             (35, 7), (36, 2), (37, 6), (38, 1), (39, 7), (40, 3), (41, 4), (42, 8), (43, 6), (44, 7), (45, 5), (46, 1),
             (47, 12), (48, 3), (49, 5), (50, 12), (51, 11), (52, 9), (53, 4), (54, 12), (55, 7), (56, 2), (57, 10),
             (58, 5), (59, 12), (60, 11), (61, 3), (62, 7), (63, 10), (64, 11), (65, 3), (66, 10), (67, 5), (68, 12),
             (69, 9), (70, 11), (71, 10), (72, 7), (73, 4), (74, 12), (75, 4), (76, 5), (77, 10), (78, 9), (79, 9),
             (80, 2), (81, 6), (82, 4), (83, 6), (84, 12), (85, 4), (86, 7), (87, 5), (88, 10), (89, 12), (90, 1),
             (91, 6), (92, 5), (93, 5), (94, 8), (95, 2), (96, 10), (97, 10), (98, 11), (99, 4), (100, 7), (101, 3),
             (102, 2), (103, 4), (104, 1), (105, 8), (106, 1), (107, 3), (108, 4), (109, 9), (110, 1), (111, 5),
             (112, 9), (113, 5), (114, 10), (115, 12)]
    G.add_edges_from(edges)
    '''
    '''
    G = nx.DiGraph()
    #dolphins
    # adauga noduri la graf
    G.add_nodes_from(range(1, 63))

    # adauga muchii la graf
    edges = [(1, 1), (2,2), (3,1), (4,1), (5,1), (6,2), (7,2), (8,2), (9,1),
             (10,2), (11,1), (12,1), (13,1), (14,2), (15,1), (16,1), (17,1),
             (18,2), (19,1), (20,1), (21,1), (22,1), (23,2), (24,1),
             (25,1), (26,2), (27,2), (28,2), (29,1), (30,1), (31,1),
             (32,2), (33,2), (34,1), (35,1), (36,1), (37,1), (38,1),
             (39,1), (40,1), (41,1), (42,2), (43,1), (44,1), (45,1),
             (46,1), (47,1), (48,1), (49,2), (50,1), (51,1), (52,1),
             (53,1), (54,1), (55,2), (56,1), (57,2), (58,2), (59,1),
             (60,1), (61,2), (62,1)]

    G.add_edges_from(edges)
    '''
    '''
    G = nx.DiGraph()
    # krebs
    # adauga noduri la graf
    G.add_nodes_from(range(1, 63))

    # adauga muchii la graf
    edges = [(1, 1), (2, 2), (3, 2), (4, 2), (5, 1), (6, 2), (7, 1), (8, 1), (9, 2),
             (10, 2), (11, 2), (12, 2), (13, 2), (14, 2), (15, 2), (16, 2), (17, 2),
             (18, 2), (19, 1), (20, 2), (21, 2), (22, 2), (23, 2), (24, 2),
             (25, 2), (26, 2), (27, 2), (28, 2), (29, 1), (30, 2), (31, 3),
             (32, 3), (33, 2), (34, 2), (35, 2), (36, 2), (37, 2), (38, 2),
             (39, 2), (40, 2), (41, 2), (42, 2), (43, 2), (44, 2), (45, 2),
             (46, 2), (47, 1), (48, 2), (49, 1), (50, 2), (51, 2), (52, 1),
             (53, 2), (54, 2), (55, 2), (56, 2), (57, 2), (58, 2), (59, 2),
             (60, 3), (61, 3), (62, 3), (63, 3), (64,3), (65, 3), (66, 3), (67,3), (68,3),
             (69,3), (70,1), (71,3), (72,3), (73,3), (74,3), (75, 3), (76,3), (77,1), (78,2), (80,3), (81, 3), (82,3),
             (83,3), (84,3), (85,3), (86, 3), (87,3), (88, 3), (89,3), (90,3), (91,3), (92,3),(93,3),
             (94,3), (95,3), (96,3), (97,3), (98,3), (99,3), (100,3), (101,3), (102, 3), (103, 3), (104, 1), (105,1)]

    G.add_edges_from(edges)
    '''
    '''
    G = nx.DiGraph()

    # adăugăm nodurile la graful nostru
    nodes = range(1, 35)
    G.add_nodes_from(nodes)

    # adăugăm muchiile la graful nostru
    edges = [(2, 1), (3, 1), (3, 2), (4, 1), (4, 2), (4, 3), (5, 1), (6, 1), (7, 1), (7, 5), (7, 6),
             (8, 1), (8, 2), (8, 3), (8, 4), (9, 1), (9, 3), (10, 3), (11, 1), (11, 5), (11, 6), (12, 1),
             (13, 1), (13, 4), (14, 1), (14, 2), (14, 3), (14, 4), (17, 6), (17, 7), (18, 1), (18, 2),
             (20, 1), (20, 2), (22, 1), (22, 2), (26, 24), (26, 25), (28, 3), (28, 24), (28, 25),
             (29, 3), (30, 24), (30, 27), (31, 2), (31, 5), (32, 8), (33, 9), (33, 10), (34, 13),
             (34, 15), (34, 16),(34,19),(34,20),(34,21),(34,23),(34,24),(34,27),(34,28),(34,29),(34,30),
             (34,31),(34,32),(34,33)]

    G.add_edges_from(edges)
    '''
    '''
    G = nx.DiGraph()

    # adăugăm nodurile la graful nostru
    nodes = range(1, 35)
    G.add_nodes_from(nodes)

    # adăugăm muchiile la graful nostru
    edges = [(1,2), (1,3), (2,3), (4,5)]

    G.add_edges_from(edges)
        '''


    G = nx.Graph()

    # adăugăm nodurile la graful nostru
    nodes = range(1, 35)
    G.add_nodes_from(nodes)

    # adăugăm muchiile la graful nostru
    edges = [(2, 1), (28, 24), (28, 25),
                     (29, 3), (30, 24), (30, 27), (31, 2), (31, 5), (32, 8), (33, 9), (33, 10), (34, 13),
                     (34, 15), (34, 16),(34,19),(34,20),(34,21),(34,23),(34,24),(34,27),(34,28),(34,29),(34,30),
                     (34,31),(34,32),(34,33)]

    G.add_edges_from(edges)

    '''
    G = nx.DiGraph()

    # adăugăm nodurile la graful nostru
    nodes = range(1, 35)
    G.add_nodes_from(nodes)

    # adăugăm muchiile la graful nostru
    '''
    '''
    G = nx.DiGraph()

    # adăugăm nodurile la graful nostru
    nodes = range(1, 35)
    G.add_nodes_from(nodes)

    # adăugăm muchiile la graful nostru
    edges = [(2, 1), (3, 1), (3, 2), (4, 1), (4, 2), (4, 3), (5, 1), (6, 1), (7, 1), (7, 5), (7, 6),
             (8, 1), (8, 2), (8, 3), (8, 4), (9, 1), (9, 3), (10, 3), (11, 1), (11, 5), (11, 6), (12, 1),
             (13, 1), (13, 4), (14, 1), (14, 2), (14, 3), (14, 4), (17, 6), (17, 7), (18, 1), (18, 2),
             (20, 1), (20, 2), (22, 1), (22, 2), (26, 24), (26, 25), (28, 3), (28, 24), (28, 25),
             (29, 3), (30, 24), (30, 27), (31, 2), (31, 5), (32, 8), (33, 9), (33, 10), (34, 13),
             (34, 15), (34, 16), (34, 19), (34, 20), (34, 21), (34, 23), (34, 24), (34, 27), (34, 28), (34, 29),
             (34, 30),
             (34, 31), (34, 32), (34, 33)]

    G.add_edges_from(edges)
    '''
    '''

    G.add_edges_from(edges)
        '''

    '''
    '''
    # Setam ponderile muchiilor
    for u, v in G.edges():
        G[u][v]['weight'] = 1
    # Aplicam algoritmul Greedy
    communities, Q = greedy_algorithm(G)

    # Afisam numarul de comunitati si valoarea modularitatii
    num_communities = len(set(communities.values()))
    print(f"Numarul de comunitati identificate: {num_communities}")
    print(f"Valoarea modularitatii: {Q}")

    # Coloram nodurile in functie de comunitatea din care fac parte si afisam reteaua
    colors = ['r', 'g', 'b', 'c', 'm', 'y', 'k']
    for i, community in enumerate(set(communities.values())):
        nodes = [node for node in G.nodes() if communities[node] == community]
        nx.draw_networkx_nodes(G, pos=nx.spring_layout(G), nodelist=nodes, node_color=colors[i % len(colors)])
    nx.draw_networkx_edges(G, pos=nx.spring_layout(G))
    plt.axis("off")
    plt.show()