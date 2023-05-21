(* Exemple de fonction(s) utilisant la bibliothèque standard:
   https://v2.ocaml.org/api/Sys.html
   https://v2.ocaml.org/api/Unix.html
   https://v2.ocaml.org/api/Filename.html *)

(* get_travail_file: string -> string
   Renvoie le fichier du sous-dossier data à partir du dossier de main.exe :
   get_travail_file "membres.csv" = "./data/membres.csv" ou bien
   get_travail_file "membres.csv" = "../data/membres.csv" si besoin... *)
let get_travail_file name =

  let exe = Sys.executable_name in
  let ( / ) = Filename.concat in
  Filename.dirname exe / "data" / name