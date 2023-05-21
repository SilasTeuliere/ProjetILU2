(* Exemple de fonctions utilisant le paquet csv,
   https://github.com/Chris00/ocaml-csv *)


(* load_csv: string -> string list list
   Lit/charge en m�moire un fichier de donn�es CSV (s�par� par des virgules) *)
let load_csv filename : string list list =
  Csv.load ~separator:',' filename


(* lines_columns: string list list -> int * int
   Retourne le (nombre de lignes exact, nombre de colonnes maximales) d'un CSV;
   il est possible que certaines lignes comportent moins de colonnes. *)
let lines_columns (data : string list list) =
  (Csv.lines data, Csv.columns data)


(* Rend les donn�es CSV rectangulaires. Cela remplit chaque ligne avec
   des cellules vides afin que toutes les lignes aient la m�me
   longueur que la ligne la plus longue. Apr�s cette op�ration, chaque
   ligne aura une longueur �gale � (snd (lines_columns data)) *)
let make_square (data : string list list) : string list list =
  Csv.square data


(* Renvoie true si le CSV est rectangulaire.
   Cela signifie que chaque ligne a le m�me nombre de cellules. *)
let is_square (data : string list list) =
  Csv.is_square data


(* map_csv: (string -> string) -> string list list -> string list list
   Applique une fonction � toutes les cellules du CSV et renvoie le r�sultat. *)
let map_csv (f : string -> string) (data : string list list) : string list list =
  Csv.map f data


(* save_csv: string -> string list list -> unit
   Enregistre dans le fichier sp�cifi� les donn�es CSV *)
let save_csv filename (data : string list list) =
  Csv.save ~separator:',' ~quote_all:true filename data


(* print_readable: string list list -> unit
   Affiche le CSV (pour faciliter le d�bogage) *)
let print_readable (data : string list list) =
  Csv.print_readable data