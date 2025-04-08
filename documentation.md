Documentation Technique de l'API "The Horror Database"
Introduction

"The Horror Database" est une API qui permet de gérer une liste de films d'horreur. Elle offre des fonctionnalités pour récupérer des listes de films, ajouter de nouveaux films, mettre à jour des informations sur les films existants, et supprimer des films.

Endpoints

1. Récupérer tous les films
Endpoint: GET /movies
Description: Récupère la liste complète des films d'horreur.
Réponse:
Statut 200 OK: Retourne une liste de films.
Statut 204 No Content: Si aucun film n'est disponible.

2. Récupérer un film par ID
Endpoint: GET /movies/{id}
Description: Récupère les détails d'un film spécifique en utilisant son ID.
Paramètres:
id (Path Variable): ID du film.
Réponse:
Statut 200 OK: Retourne les détails du film.
Statut 404 Not Found: Si le film n'existe pas.

3. Récupérer les films d'un utilisateur
Endpoint: GET /movies/user/{userId}
Description: Récupère les films associés à un utilisateur spécifique.
Paramètres:
userId (Path Variable): ID de l'utilisateur.
Authorization (Header): Jeton JWT pour l'authentification.
Réponse:
Statut 200 OK: Retourne la liste des films de l'utilisateur.
Statut 403 Forbidden: Si l'utilisateur n'est pas autorisé.

4. Ajouter un nouveau film
Endpoint: POST /movies
Description: Ajoute un nouveau film à la base de données.
Paramètres:
Authorization (Header): Jeton JWT pour l'authentification.
Body: Objet Movie contenant les détails du film.
Réponse:
Statut 201 Created: Retourne les détails du film créé.

5. Mettre à jour un film
Endpoint: PUT /movies/{id}
Description: Met à jour les informations d'un film existant.
Paramètres:
id (Path Variable): ID du film.
Body: Objet Movie contenant les détails mis à jour.
Réponse:
Statut 200 OK: Retourne les détails du film mis à jour.
Statut 404 Not Found: Si le film n'existe pas.

6. Supprimer un film
Endpoint: DELETE /movies/{id}
Description: Supprime un film de la base de données.
Paramètres:
id (Path Variable): ID du film.
Authorization (Header): Jeton JWT pour l'authentification.
Réponse:
Statut 204 No Content: Si le film a été supprimé avec succès.
Statut 404 Not Found: Si le film n'existe pas.

Authentification

L'API utilise l'authentification JWT pour sécuriser les endpoints. Les utilisateurs doivent inclure un jeton JWT valide dans l'en-tête Authorization pour accéder aux endpoints protégés.

Ajouter un nouveau film

Endpoint: POST /movies
Protection: Accessible uniquement aux utilisateurs avec les rôles USER ou ADMIN.
Mettre à jour un film

Endpoint: PUT /movies/{id}
Protection: Accessible uniquement aux utilisateurs avec le rôle ADMIN.
Supprimer un film

Endpoint: DELETE /movies/{id}
Protection: Accessible uniquement aux utilisateurs avec le rôle ADMIN.
Récupérer les films d'un utilisateur

Endpoint: GET /movies/user/{userId}
Protection: Accessible uniquement aux utilisateurs authentifiés, et l'utilisateur doit correspondre à l'ID spécifié.
Ajouter une critique

Endpoint: POST /reviews
Protection: Accessible uniquement aux utilisateurs avec les rôles USER ou ADMIN.
Modifier une critique

Endpoint: PATCH /reviews/{id}
Protection: Accessible uniquement aux utilisateurs avec les rôles USER ou ADMIN.
Supprimer une critique

Endpoint: DELETE /reviews/{id}
Protection: Accessible uniquement aux utilisateurs avec les rôles USER ou ADMIN.
Ajouter un favori

Endpoint: POST /favorites
Protection: Accessible uniquement aux utilisateurs avec les rôles USER ou ADMIN.
Supprimer un favori

Endpoint: DELETE /favorites/{id}
Protection: Accessible uniquement aux utilisateurs avec les rôles USER ou ADMIN.
Supprimer un utilisateur

Endpoint: DELETE /users/{id}
Protection: Accessible uniquement aux utilisateurs avec le rôle ADMIN.
Accéder au profil utilisateur

Endpoint: GET /auth/profile/{id}
Protection: Accessible uniquement aux utilisateurs avec les rôles USER ou ADMIN.
