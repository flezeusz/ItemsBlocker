# ItemsBlocker

**ItemsBlocker** to plugin do serwerów Minecraft (Paper 1.20.4), który umożliwia administratorom blokowanie określonych przedmiotów, zaklęć i efektów mikstur. Plugin oferuje zestaw komend do zarządzania blokadami.

## Funkcje

* **Blokowanie Przedmiotów:** Dodawanie/usuwanie przedmiotów z listy zablokowanych.
* **Blokowanie Zaklęć:** Blokowanie zaklęć z opcjonalnym określeniem minimalnego poziomu.
* **Blokowanie Efektów Mikstur:** Blokowanie efektów mikstur z opcjonalnym określeniem minimalnego poziomu.
* **Blokowanie Craftingu Netherytu:** Włączanie/wyłączanie możliwości craftowania netherythowych przedmiotów.

## Komendy

* `/block` lub `/itemsblocker` -  Główny alias komendy. Wymagane uprawnienie: `itemsblocker.command`

### Podkomendy

* `/block help`: Wyświetla listę dostępnych komend.

#### Zarządzanie Przedmiotami

* `/block item add <przedmiot>`: Dodaje przedmiot do zablokowanych (np. `/block item add DIAMOND_SWORD`).
* `/block item remove <przedmiot>`: Usuwa przedmiot z zablokowanych.
* `/block item list`: Wyświetla listę zablokowanych przedmiotów.

#### Zarządzanie Zaklęciami

* `/block enchantment add <nazwa_zaklęcia> [poziom]`: Dodaje zaklęcie do zablokowanych (np., `/block enchantment add riptide 1`).
    * `<nazwa_zaklęcia>`: Nazwa zaklęcia (użyj nazw z Minecraft, np. `sharpness`, `efficiency`).
    * `[poziom]` (Opcjonalne): Minimalny poziom zaklęcia do zablokowania. Domyślnie: 1.
* `/block enchantment remove <nazwa_zaklęcia>`: Usuwa zaklęcie z zablokowanych.
* `/block enchantment list`: Wyświetla listę zablokowanych zaklęć i poziomów.

#### Zarządzanie Efektami Mikstur

* `/block potion add <nazwa_efektu> [poziom]`: Dodaje efekt mikstury do zablokowanych (np., `/block potion add strenght 2`).
    * `<nazwa_efektu>`: Nazwa efektu mikstury (użyj nazw z Minecraft, np. `speed`, `strength`).
    * `[poziom]` (Opcjonalne): Minimalny poziom efektu do zablokowania. Domyślnie: 1.
* `/block potion remove <nazwa_efektu>`: Usuwa efekt mikstury z zablokowanych.
* `/block potion list`: Wyświetla listę zablokowanych efektów mikstur i poziomów.

#### Zarządzanie Craftingiem Netherytu

* `/block netherite <true|false>`: Włącza/wyłącza blokowanie craftingu przedmiotów z netherytu.

## Uprawnienia

* `itemsblocker.command`:  Uprawnienie wymagane do używania wszystkich komend pluginu.
* `itemsblocker.bypass`:  Uprawnienie do omijania wszystkich blokad itemów.

## Instalacja

1.  Pobierz plik `.jar` pluginu ItemsBlocker.
2.  Umieść plik `.jar` w folderze `plugins` Twojego serwera Minecraft (Paper 1.20.4).
3.  Zrestartuj serwer.
