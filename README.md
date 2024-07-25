# MobLoot Plugin

MobLoot ist ein Minecraft-Plugin, das es ermöglicht, benutzerdefinierte Loot-Drops für verschiedene Mobs zu konfigurieren. Zusätzlich zeigt es die Lebenspunkte von Mobs an und bietet viele weitere Anpassungsoptionen.

## Funktionen

- **Konfigurierbare Loot-Raten**: Stellen Sie die Drop-Raten für verschiedene Mobs ein.
- **Mehrere Drop-Items**: Erlauben Sie Mobs, mehrere verschiedene Items mit unterschiedlichen Wahrscheinlichkeiten fallen zu lassen.
- **Spezielle Drop-Events**: Implementieren Sie spezielle Events, bei denen Mobs seltene oder besondere Items fallen lassen können.
- **Loot-Log**: Protokollieren Sie, welche Items von welchen Mobs fallen gelassen wurden und von wem sie abgeholt wurden.
- **Benutzerdefinierte GUI**: Eine benutzerfreundliche GUI zur Verwaltung der Drops.
- **Lebenspunkteanzeige**: Zeigt die Lebenspunkte der Mobs an und kann per Befehl aktiviert oder deaktiviert werden.
- **Drop-Liste**: Zeigt eine Liste aller Drops für jeden Mob an.

## Installation

1. **Download**: Laden Sie die neueste Version des Plugins von [GitHub Releases](https://github.com/kruemmelJava/MobLoot-Plugin) herunter.
2. **Installation**: Kopieren Sie die heruntergeladene `.jar`-Datei in das `plugins`-Verzeichnis Ihres Minecraft-Servers.
3. **Server Neustarten**: Starten Sie Ihren Minecraft-Server neu, um das Plugin zu aktivieren.

## Konfiguration

Das Plugin erstellt automatisch eine `drops.yml`-Datei im `plugins/MobLoot`-Verzeichnis. Diese Datei können Sie bearbeiten, um die Loot-Drops zu konfigurieren.

### Beispiel für `drops.yml`

```yaml
showHealthBars: true
rates:
  ZOMBIE: 1.0
  SKELETON: 0.5
drops:
  ZOMBIE:
    - item: DIAMOND
      chance: 0.1
    - item: GOLD_INGOT
      chance: 0.2
  SKELETON:
    - item: ARROW
      chance: 0.5
    - item: BONE
      chance: 1.0
specialDrops:
  ZOMBIE:
    - item: EMERALD
      chance: 0.05
  SKELETON:
    - item: DIAMOND
      chance: 0.01
```
Befehle
/setdrops
Öffnet eine GUI, in der Sie die Loot-Drops für verschiedene Mobs einstellen können.

/healthbar [on|off]
Aktiviert oder deaktiviert die Lebenspunkteanzeige für Mobs.

/listdrops
Zeigt eine Liste der Items an, die von jedem Mob fallen gelassen werden.

/specialevent [start|stop|addloot]
Verwaltet spezielle Events für Mob-Drops.

/specialevent start: Startet ein spezielles Event.
/specialevent stop: Stoppt ein spezielles Event.
/specialevent addloot <mobType> <item> <chance>: Fügt einem Mob einen speziellen Event-Drop hinzu.
Spielanleitung
Loot-Drops einstellen
Geben Sie im Spiel den Befehl /setdrops ein.
Eine GUI mit einer Liste von Mobs öffnet sich.
Wählen Sie einen Mob aus der Liste aus.
Eine weitere GUI mit allen verfügbaren Items öffnet sich.
Wählen Sie ein Item aus, das der Mob fallen lassen soll.
Das Item wird als Drop für den ausgewählten Mob konfiguriert.
Lebenspunkteanzeige
Geben Sie im Spiel den Befehl /healthbar on ein, um die Lebenspunkteanzeige zu aktivieren.
Geben Sie den Befehl /healthbar off ein, um die Lebenspunkteanzeige zu deaktivieren.
Drop-Liste anzeigen
Geben Sie im Spiel den Befehl /listdrops ein.
Eine Liste der Items, die von jedem Mob fallen gelassen werden, wird angezeigt.
Spezielle Drop-Events
Geben Sie im Spiel den Befehl /specialevent start ein, um ein spezielles Event zu starten.
Geben Sie den Befehl /specialevent stop ein, um ein spezielles Event zu stoppen.
Geben Sie den Befehl /specialevent addloot <mobType> <item> <chance> ein, um einem Mob einen speziellen Event-Drop hinzuzufügen.
Loot-Log
Das Plugin protokolliert automatisch, welche Items von welchen Mobs fallen gelassen wurden und von wem sie abgeholt wurden. Diese Informationen werden im Server-Log gespeichert.

Entwickler
Ralf Krümmel
Lizenz
Dieses Projekt ist lizenziert unter der MIT-Lizenz - siehe die LICENSE.md Datei für Details.


