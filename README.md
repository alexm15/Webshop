# Webshop - Fashionshoppen

Vores überseje kode til vores semesterprojekt.


## Versions nummering af projektet.
Forslag om at implementere et versionnummeringssystem til vores projekt for at gøre det nemt at holde styr på hvad vi har lavet og hvordan vores projekt har set ud på et givet tidspunkt.

### Forslaget:
Webshop v1.0.1, hvor syntaksen vil være v[Major].[Minor].[Patch]

[Major] = Ændringer hvor det ikke er muligt at gå tilbage til et tidligere nummer. Under disse fx v2.0.0 kan ikke gå tilbage til v1.0.0. Er også alt der har med endelig release at gøre.

[Minor] = Ændringer der ikke ødelægger kompatabilitet med tidligere numre af denne type, så v1.2.0 kan godt gå tilbage til v1.1.0. Her øges nummeret typisk når til tilføjes nye features til produktet.

[Patch] = Alle bugfixes der laves på systemet. Fx formatteringsrettelser, bugs i features, sikkerhedstilføjelser (exeption-handling til metoder).

Eksempel på hændelsesforløb.
* v0.1.0 = Nye klasser med funktionalitet tilføjet
* v0.1.1 = Løsning af sikkerhedshuller på de nuværende features
* v0.2.0 = Flere klasser tilføjet og ny funktionalitet tilføjet til eksisterende klasser
* v1.0.0 = Første officiel udgivelse af produktet
