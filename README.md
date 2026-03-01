# TP — Injection de Dépendances (Dependency Injection)

> **Cours :** Architecture Logicielle / Conception Orientée Objet  
> **Langage :** Java  
> **Auteur :** GHIZLANETHR  
> **Repository :** [TP-Injection-Dependances](https://github.com/GHIZLANETHR/TP-Injection-Dependances)

---

## Table des matières

- [Présentation](#-présentation)
- [Concepts théoriques](#-concepts-théoriques)
- [Structure du projet](#-structure-du-projet)
- [Prérequis](#-prérequis)
- [Installation et exécution](#-installation-et-exécution)
- [Détail du TP1](#-détail-du-tp1)
- [Types d'injection illustrés](#-types-dinjection-illustrés)
- [Principes SOLID appliqués](#-principes-solid-appliqués)


---

## Présentation

Ce projet est un travail pratique (TP) académique portant sur le **patron de conception Injection de Dépendances** (*Dependency Injection — DI*) en Java.

L'injection de dépendances est un principe fondamental de la programmation orientée objet moderne. Elle permet de **découpler** les composants d'une application en déléguant la création et la liaison des objets à un élément externe (un *conteneur* ou le code appelant), plutôt que de laisser chaque classe instancier elle-même ses dépendances.

Ce TP explore les différentes approches de l'injection de dépendances : manuelle (sans framework), puis potentiellement à l'aide de frameworks comme **Spring**.

---

## Concepts théoriques

### Qu'est-ce qu'une dépendance ?

Une classe A **dépend** d'une classe B si elle a besoin de B pour fonctionner. Exemple :

```java
// ❌ Couplage fort — A crée directement sa dépendance
public class OrderService {
    private EmailNotifier notifier = new EmailNotifier(); // dépendance "hardcodée"
}
```

### Qu'est-ce que l'injection de dépendances ?

Au lieu de créer sa propre dépendance, la classe la **reçoit de l'extérieur** :

```java
// Couplage faible — la dépendance est injectée
public class OrderService {
    private INotifier notifier;

    public OrderService(INotifier notifier) {
        this.notifier = notifier; // injection par constructeur
    }
}
```

### Avantages

- **Testabilité** : on peut injecter des mocks ou stubs lors des tests unitaires.
- **Flexibilité** : changer d'implémentation sans modifier le code consommateur.
- **Maintenabilité** : séparation claire des responsabilités.
- **Respect des principes SOLID**, notamment l'inversion de dépendances (DIP).

---

## Structure du projet

```
TP-Injection-Dependances/
│
├── TP1/                        # Premier travail pratique
│   ├── src/
│   │   ├── dao/                # Couche d'accès aux données (Data Access Object)
│   │   │   ├── IDao.java       # Interface du DAO
│   │   │   └── DaoImpl.java    # Implémentation concrète du DAO
│   │   │
│   │   ├── metier/             # Couche métier (Business Logic)
│   │   │   ├── IMetier.java    # Interface métier
│   │   │   └── MetierImpl.java # Implémentation de la logique métier
│   │   │
│   │   └── presentation/       # Couche de présentation / point d'entrée
│   │       ├── MainStatique.java   # DI par instanciation statique (manuelle)
│   │       ├── MainDynamique.java  # DI dynamique via fichier de config / réflexion
│   │       └── config.txt          # Fichier de configuration des classes à instancier
│   │
│   └── README.md
│
└── README.md                   # Ce fichier
```

> **Note :** La structure ci-dessus est indicative et basée sur les conventions standard des TPs d'injection de dépendances en Java. Se référer aux fichiers réels du repository pour la structure exacte.

---

## Prérequis

Avant de cloner et d'exécuter ce projet, assurez-vous d'avoir installé :

| Outil | Version minimale | Lien |
|-------|-----------------|------|
| **JDK (Java Development Kit)** | 8+ | [Télécharger JDK](https://www.oracle.com/java/technologies/javase-downloads.html) |
| **IDE recommandé** | — | [IntelliJ IDEA](https://www.jetbrains.com/idea/) ou [Eclipse](https://www.eclipse.org/) |
| **Git** | 2.x | [Télécharger Git](https://git-scm.com/) |

---

##  Installation et exécution

### 1. Cloner le repository

```bash
git clone https://github.com/GHIZLANETHR/TP-Injection-Dependances.git
cd TP-Injection-Dependances
```

### 2. Ouvrir dans un IDE

Importez le dossier `TP1` comme projet Java dans votre IDE favori (IntelliJ IDEA, Eclipse, VS Code avec extension Java).

### 3. Compiler et exécuter

**Depuis la ligne de commande :**

```bash
cd TP1/src
javac dao/*.java metier/*.java presentation/*.java

# Exécuter la version statique
java presentation.MainStatique

# Exécuter la version dynamique
java presentation.MainDynamique
```

**Depuis un IDE :** Lancer directement la classe `MainStatique` ou `MainDynamique` en tant que programme Java.

---

## Détail du TP1

### Architecture en couches

Le TP suit une architecture **3-tiers** classique :

```
[ Présentation ] → [ Métier ] → [ DAO ]
```

Chaque couche communique avec la couche inférieure **uniquement via des interfaces**, ce qui permet le découplage.

### Couche DAO (`dao/`)

```java
// Interface — contrat que toute implémentation doit respecter
public interface IDao {
    double getData();
}

// Implémentation concrète — peut être remplacée sans toucher au métier
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("Version Base de Données");
        return 23;
    }
}
```

### Couche Métier (`metier/`)

```java
public interface IMetier {
    double calcul();
}

public class MetierImpl implements IMetier {
    private IDao dao; // dépendance vers l'interface, pas l'implémentation

    // Injection par setter
    public void setDao(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        double tmp = dao.getData();
        // ... logique métier ...
        return tmp * 10;
    }
}
```

### Injection Statique (`MainStatique.java`)

La dépendance est créée et injectée manuellement dans le code :

```java
public class MainStatique {
    public static void main(String[] args) {
        // 1. Créer la dépendance
        DaoImpl dao = new DaoImpl();

        // 2. Créer l'objet métier
        MetierImpl metier = new MetierImpl();

        // 3. Injecter la dépendance
        metier.setDao(dao);

        // 4. Utiliser
        System.out.println("Résultat : " + metier.calcul());
    }
}
```

### Injection Dynamique (`MainDynamique.java`)

Les classes à instancier sont lues depuis un fichier de configuration, et instanciées par **réflexion Java** :

```java
// config.txt contient par exemple :
// dao.DaoImpl
// metier.MetierImpl

public class MainDynamique {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("config.txt"));

        // Charger dynamiquement la classe DAO
        String daoClassName = scanner.nextLine();
        Class cDao = Class.forName(daoClassName);
        IDao dao = (IDao) cDao.newInstance();

        // Charger dynamiquement la classe Métier
        String metierClassName = scanner.nextLine();
        Class cMetier = Class.forName(metierClassName);
        IMetier metier = (IMetier) cMetier.newInstance();

        // Injection via réflexion — chercher la méthode setDao
        Method method = cMetier.getMethod("setDao", IDao.class);
        method.invoke(metier, dao);

        System.out.println("Résultat : " + metier.calcul());
    }
}
```

> **Avantage :** Pour changer d'implémentation (ex: `DaoImplV2`), il suffit de modifier `config.txt` **sans recompiler** le code.

---

##  Types d'injection illustrés

### 1. Injection par Setter (méthode `setXxx`)

```java
metier.setDao(dao);
```
**Avantage :** Flexible, la dépendance peut être changée après construction.  
**Inconvénient :** L'objet peut exister dans un état incomplet si le setter n'est pas appelé.

### 2. Injection par Constructeur

```java
public MetierImpl(IDao dao) {
    this.dao = dao;
}
```
**Avantage :** Garantit que l'objet est toujours dans un état valide dès sa création.  
**Inconvénient :** Moins flexible si beaucoup de dépendances.

### 3. Injection par Interface (moins courante)

La classe implémente une interface spécifique pour recevoir sa dépendance.

---

## Principes SOLID appliqués

| Principe | Description | Application dans le TP |
|----------|-------------|------------------------|
| **S** — Single Responsibility | Chaque classe a une seule responsabilité | DAO gère les données, Métier gère la logique |
| **O** — Open/Closed | Ouvert à l'extension, fermé à la modification | Ajouter `DaoImplV2` sans modifier `MetierImpl` |
| **L** — Liskov Substitution | Une implémentation peut remplacer son interface | `DaoImpl` remplaçable par n'importe quel `IDao` |
| **I** — Interface Segregation | Interfaces spécifiques et cohérentes | `IDao` et `IMetier` sont séparées |
| **D** — Dependency Inversion | Dépendre des abstractions, pas des implémentations | `MetierImpl` dépend de `IDao`, pas de `DaoImpl` |

---



