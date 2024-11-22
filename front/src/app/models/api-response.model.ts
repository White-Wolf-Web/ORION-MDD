export interface ApiResponse<T> {
    data: T;                           // Données renvoyées par l'API
    message: string;                   // Message décrivant le statut de la réponse
    status: string;                    // Succès ou échec (exemple : "success", "error")
  }
  