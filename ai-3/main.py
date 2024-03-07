import networkx as nx
import random

import partitions as partitions


def fitness(partition, G):
    """
    Calculează modularity score pentru o partiție dată și o rețea dată.
    """
    m = G.number_of_edges()
    q = 0
    for c in nx.connected_components(partition):
        subgraph = G.subgraph(c)
        lc = subgraph.number_of_edges()
        dc = sum(dict(G.degree(c)).values())
        q += (lc / m) - ((dc / (2 * m)) ** 2)
    return q

def crossover(parent1, parent2):
    """
    Realizează crossover-ul între doi părinți și creează un copil.
    """
    child = parent1.copy()
    keys = list(child.keys())
    random.shuffle(keys)
    for key in keys:
        if parent2[key] != child[key]:
            child[key] = parent2[key]
    return child

def mutation(partition):
    """
    Realizează o mutație aleatoare asupra unei partiții.
    """
    new_partition = partition.copy()
    node = random.choice(list(new_partition.keys()))
    new_community = random.choice(list(set(new_partition.values()) - {new_partition[node]}))
    new_partition[node] = new_community
    return new_partition

# Construim rețeaua și setăm parametrii algoritmului
G = nx.karate_club_graph()
population_size = 100
mutation_rate = 0.1
generations = 100

# Inițializăm populația
population = [dict(zip(G.nodes(), [random.randint(0, population_size-1) for _ in range(G.number_of_nodes())]))
              for _ in range(population_size)]

# Iterăm prin generații
for _ in range(generations):
    # Evaluăm fitness-ul pentru fiecare individ din populație
    fitness_scores = [(p, fitness(p, G)) for p in population]
    # Selectăm cei mai buni părinți pentru reproducere
    parents = [p[0] for p in sorted(fitness_scores, key=lambda x: x[1], reverse=True)[:int(population_size/2)]]
    # Cream copii prin aplicarea crossover-ului între părinți
    children = []
    for i in range(int(population_size/2)):
        parent1 = random.choice(parents)
        parent2 = random.choice(parents)
        child = crossover(parent1, parent2)
        # Aplicarea mutației
        if random.random() < mutation_rate:
            child = mutation(child)
        children.append(child)
    # Îmbinarea părinților și copiilor pentru a forma noua populație
    population = parents + children

# Afișarea rezultatului final
best_partition = max(population, key=lambda p: fitness(p, G))
print(best_partition)
# determinăm numărul de comunități
num_communities = len(set(partitions.values()))
print(f"Numărul de comunități este: {num_communities}")