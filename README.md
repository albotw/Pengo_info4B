# projet_info4B_2019_2020
Portage de Pengo java avec application des principes des SE. Fait dans le cadre de l'info4B

## 1| Contraintes:
Le projet est a faire en **Java**, une interface graphique n'est pas nécessaire.

le code doit être conçu de façon modulaire: encapsulation stricte des packages / interfaces / classes et utilisation de classes utilitaires / constantes / fichiers de config.

Le code doit refléter la conception en couches d'un SE. Les structures de données ne sont pas forcément dynamiques -> une utilisation des collections proposées par java est recommandée.
##### L'emploi de static devra être justifié.

Le respect des conventions d'écriture en Java est **fortement** recommandé car la lisibilité du code est notée. (https://www.jmdoudoux.fr/java/dej/chap-normes-dev.htm).


## 2| Rapport:
### La présentation du projet ainsi que la remise du rapport aura lieu la semaine du 13 AVRIL
### Le rapport doit être rédigé en Latex
Consignes de présentation:
* 15 pages au minimum
* police 12pt MAX
* marges 2cm MAX

Contenu:
* **Analyse fonctionnelle** du sujet précisant les règles de fonctionnement, découpage du problème en sous problèmes, entre autre les classes les plus importantes et leurs liens. 
* **Description des structures de données** envisagée et retenues.
* **Spécification des classes principales** et les méthodes essentielles
* **Architecture logicielle détaillée** description des couches fonctionelles, les classes les composant, et les services réalisés.
* **Algorimthes principaux** détaillées
* **com.generic.coreClasses.Jeu de tests** pour montrer que le programme marche


## 3| Projet:
###  Gameplay:
Le but du projet est de refaire Pengo, jeu d'arcade des années 80. 

Un pingouin évolue dans un labyrinthe constitué de blocs de glace. 

Plusieurs animaux tentent de le capturer.

Le pingouin peut déplacer les blocs de glace en les poussant. Les blocs suivent alors une trajectoire rectiligne et s'arrêtent de glisser s'ils rencontrent un autre bloc ou le mur.

Si le pingouin pousse un bloc qui touche un autre bloc, le bloc poussé est détruit.

Si la trajectoire du bloc en déplacement touche un animal,  celui ci sera détruit. Un nouvel animal viendra le remplacer quelques secondes plus tard.

La fin du jeu intervient lorsque trois blocs spéciaux (diamant) se retrouvent alignés. ces blocs sont indestructibles.

Le score est calculé en fonction du nombre d'animaux tués et le temps de jeu.

### Fonctionnalités:
Les animaux doivent être controlés par des joueurs ou par l'IA.

Dans le cas de l'IA, plusieurs comportement doivent être implémentés (défense des blocs spéciaux, aggression du pingouin, cassage de blocs autour des blocs spéciaux, etc...)

Un mode coop vs IA en réseau devra être fait, avec plusieurs pingouins sur le plateau

Les scores doivent être enregistrés sur un serveur et la liste des meilleurs joueurs doit être maintenue a jour.

Il doit être possible de faire des équipes en mode collaboratif et d'affronter d'autres équipes manipulant les animaux.

#### En option:
Les participants doivent pouvoir proposer eux même des stratégies de déplacement des animaux sous la forme de classes java qui seront chargés dynamiquement sur le serveur. Il faudra utiliser l'API reflexion. Le comportement des classes sera conforme a une interface commune.

### Références:
http://en.wikipedia.org/wiki/Pengo_(arcade_game)
http://www.youtube.com/watch?v=4Mw-XkalHUg
https://www.youtube.com/watch?v=T3YlUYsu7u0
