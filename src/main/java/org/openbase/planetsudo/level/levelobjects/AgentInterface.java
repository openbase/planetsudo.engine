/*
 * Copyright (C) 2013 DivineCorporation
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openbase.planetsudo.level.levelobjects;

import java.util.Collection;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.planetsudo.game.SwatTeam;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2018 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
/**
 *
 * @author Divine Threepwood
 */
public interface AgentInterface {

    /**
     * Der Angeforderte Support des Agenten wird beendet.
     * Treibstoff: 1
     * Aktionspunkte: 5
     */
    void cancelSupport();

    /**
     * Der Agent &uuml;bergibt die Resource dem Mutterschiff.
     * Treibstoff: 1
     * Aktionspunkte: 10
     */
    void deliverResourceToMothership();

    /**
     * Eine Markierung, sichtbar für das ganze Team wird gesetzt. Alle Agenten
     * des Teams können sich auf diesen zubewegen.
     * Treibstoff: 1
     * Aktionspunkte: 5
     */
    void deployMarker();

    /**
     * Der Agent legt seine Mine an seiner aktuellen Position ab.
     * Aktionspunkte: 50
     * Treibstoff: 5
     */
    void deployMine();

    /**
     * Der Befehl zum bek&auml;mpfen eines feindlichen Agenten.
     * Wenn ein Agent einen anderen erfolgreich angreift, bekommt er eine Treibstoffbelohnung.
     * Aktionspunkte: 21
     * Treibstoff: 1
     */
    void fightWithAdversaryAgent();

    /**
     * Der Befehl zum Angreifen des feindlichen Mutterschiffs.
     * Aktionspunkte: 30
     * Treibstoff: 1
     */
    void fightWithAdversaryMothership();

    /**
     * Gibt die verf&uuml;gbaren Aktionspunkte wieder.
     *
     * @return Die Aktionspunkte als ganze Zahl. (z.B. 2051)
     */
    int getActionPoints();

    /**
     * Gibt die Richtung des Agenten in Grad wieder. Rechts = 0° Runter = 90°
     * Links = 180° Hoch = 270°
     *
     * @return Den Winkel von 0 - 360° als ganze Zahl. (z.B. 128)
     */
    int getAngle();

    /**
     * Zeigt den verbliebenen Treibstoff des Agenten an.
     *
     * @return Den verbliebenen Treibstoff als ganz Zahl.
     */
    int getFuel();

    /**
     * Gibt den verbliebenen Treibstoff in Prozent an.
     *
     * @return Treibstoffwert in Prozent als ganze Zahl. (z.B. 47 bei 47%
     * verbliebenen Treibstoff)
     */
    int getFuelInPercent();

    /**
     * Gibt das Maximalvolumen vom Treibstoff eines Agenten an.
     *
     * @return
     */
    int getFuelVolume();

    /**
     * Gibt den Typ der Resource wieder, welche der Agent ber&uuml;hrt.
     *
     * @return Den Ressourcenwert
     */
    Resource.ResourceType getResourceType();

    /**
     * Gibt die Punkte des Teams wieder.
     *
     * @return Den Punktestand als ganze Zahl (z.B. 47)
     */
    int getTeamPoints();

    /**
     * Der Agent bewegt sich geradeaus.
     * Aktionspunkte: 3 + 3 wenn resource geladen
     * Treibstoff: 1
     */
    void go();

    /**
     * Der Agent dreht sich um {
     * Aktionspunkte: 3 + 3 wenn resource geladen
     * Treibstoff: 1
     * {@code beta}° nach links und bewegt sich anschließend geradeaus.
     *
     * @param beta Drehwinkel
     */
    void goLeft(int beta);

    /**
     * Der Agent dreht sich um {@code beta}° nach rechts und bewegt sich anschließend geradeaus.
     * Aktionspunkte: 3 + 3 wenn resource geladen
     * Treibstoff: 1
     *
     * @param beta Drehwinkel
     */
    void goRight(int beta);

    /**
     * Der Agent bewegt sich in Richtung des Markers, falls dieser gesetzt
     * wurde. Hierbei wird ein Weg berechnet, welcher auf k&uuml;rzester Distanz
     * zum Marker f&uuml;hrt. Um Hindernisse bewegt sich der Agent hierbei
     * automatisch herum.
     * Aktionspunkte: 4 + 4 wenn resource geladen
     * Treibstoff: 1
     */
    void goToMarker();

    /**
     * Der Agent bewegt sich auf das Mutterschiff zu. Hierbei wird ein Weg
     * berechnet, welcher auf k&uuml;rzester Distanz zum Mutterschiff
     * f&uuml;hrt. Um Hindernisse bewegt sich der Agent hierbei automatisch
     * herum.
     * Aktionspunkte: 4 + 4 wenn resource geladen
     * Treibstoff: 1
     */
    void goToMothership();

    /**
     * Der Agent bewegt sich zur Resource.
     * Aktionspunkte: 4 + 4 wenn resource geladen
     * Treibstoff: 1
     */
    void goToResource();

    /**
     * Der Agent bewegt sich zur Resource vom angegebenen Typen.
     * Aktionspunkte: 4 + 4 wenn resource geladen
     * Treibstoff: 1
     * @param resourceType
     */
    public void goToResource(final Resource.ResourceType resourceType);
    
    /**
     * Gehe zu einem Agenten der Hilfe benötigt.
     * Aktionspunkte: 4 + 4 wenn resource geladen
     * Treibstoff: 1
     */
    void goToSupportAgent();

    /**
     * Gibt zur&uuml;ck, ob der Agent Treibstoff hat.
     *
     * @return true oder false.
     */
    boolean hasFuel();

    /**
     * Gibt zur&uuml;ck, ob der Agent seine Mine noch tr&auml;gt.
     *
     * @return true oder false.
     */
    boolean hasMine();

    /**
     * Gibt an, ob der Agent noch lebt.
     *
     * @return true oder false.
     */
    boolean isAlive();

    /**
     * Gibt zur&uuml;ck, ob der Agent beim Mutterschiff ist.
     *
     * @return true oder false.
     */
    boolean isAtMothership();

    /**
     * Zeigt an, ob der Agent eine Resource trägt.
     *
     * @return true oder false
     */
    boolean isCarringResource();

    /**
     * Gibt an ob der Agent eine Resource vom Typ {
     *
     * {@code type} trägt.
     *
     * @param type
     * @return true oder false.
     */
    boolean isCarringResource(final Resource.ResourceType type);

    /**
     * Gibt an, ob ein Zusammensto&szlig; mit einer Wand bevorsteht.
     *
     * @return true oder false.
     */
    boolean isCollisionDetected();

    /**
     * Gibt zurück, ob der Agent ein Commander ist oder nicht.
     *
     * @return true oder false
     */
    boolean isCommander();

    /**
     * Gibt zur&uuml;ck, ob der Agent Bewegungsunf&auml;hig ist. (Zerst&ouml;rt
     * oder ohne Treibstoff)
     *
     * @return true oder false.
     */
    boolean isDisabled();

    /**
     * Abfrage, ob der Agent sich im Kampf befindet oder nicht.
     *
     * @return true oder false.
     */
    boolean isFighting();

    /**
     * Abfrage, ob dieser agent Support angefordert hat!
     * !!! Nicht of irgend einer Support angefordert hat!!!
     * Hierfür mothership.needSomeoneSupport(); benutzen.
     *
     * @return true oder false
     */
    boolean isSupportOrdered();

    /**
     * Diese Abfrage checkt das bevorstehende Ende des Spiels. Wenn es bald
     * vorbei ist gibt sie true zurück, dann sollte man schleunigst zum
     * Mutterschiff.
     *
     * @return true oder false
     */
    boolean isGameOverSoon();

    /**
     * Gibt zur&uuml;ck, ob der Agent eine Resource ber&uuml;hrt und diese somit
     * aufgeben kann.
     *
     * @return true oder false.
     */
    boolean isTouchingResource();

    /**
     * Gibt zur&uuml;ck, ob der Agent eine Resource vom Typ {@code type} ber&uuml;hrt und diese somit aufgeben kann.
     *
     * @param type
     * @return
     */
    boolean isTouchingResource(final Resource.ResourceType type);

    /**
     * Gibt an, ob der Agent unter Beschuss steht.
     *
     * @return true oder false.
     */
    boolean isUnderAttack();

    /**
     * Der Agent fordert {@code percent} Prozent Treibstoff vom Mutterschiff an.
     *
     * @param percent Der anzufordernde Treibstoffwert in Prozent als ganze Zahl
     * von 0 - 100.
     * Aktionspunkte: 20 + 2 für jeden getankten Treibstoff.
     */
    void orderFuel(int percent);

    /**
     * Befehl zum Aussenden des Hilferufs.
     * Treibstoff: 0
     * Aktionspunkte: 5
     */
    void orderSupport();

    /**
     * Der Befehl zum aufnehmen einer Resource wenn der Agent sie anfassen kann.
     * Treibstoff: 1
     * Aktionspunkte: 10 + Normal(200), DoublePoints(400), ExtremPoint(700), ExtraAgentFuel(400), ExtraMothershipFuel(400), Mine(200)
     */
    void pickupResource();

    /**
     * Wenn der Agent eine Resource trägt, lässt er sie wieder fallen.
     * Treibstoff: 0
     * Aktionspunkte: 0
     */
    void releaseResource();

    /**
     * Der Befehl zum reparieren des eigenen Mutterschiffs. Hierzu muss sich der
     * Agent am Mutterschiff befinden!
     * Aktionspunkte: 30
     * Treibstoff: 1
     */
    void repairMothership();

    /**
     * Der Agent dreht sich einmal im Kreis. Sieht er während der Drehung eine
     * Resource, unterbricht er und schaut in ihre Richtung.
     * Aktionspunkte: 10 pro 20° Drehung
     * Treibstoff: 1 pro 20° Drehung
     */
    void searchResources();

    /**
     * Zeigt an, ob der Agent einen feindlichen Agenten sieht.
     *
     * @return true oder false.
     */
    boolean seeAdversaryAgent();

    /**
     * Zeigt an, ob der Agent das feindliche Mutterschiff sieht.
     *
     * @return true oder false.
     */
    boolean seeAdversaryMothership();

    /**
     * Zeigt an, ob sich in Sicht des Agenten ein Teammitglied ohne Treibstoff
     * befindet.
     *
     * @return true oder false.
     */
    boolean seeLostTeamAgent();

    /**
     * Gibt zurück, ob der Agent den Marker sieht.
     *
     * @return true oder false
     */
    boolean seeMarker();
    
    /**
     * Gibt zurück, ob der Agent den Turm sieht.
     *
     * @return true oder false
     */
    boolean seeTower();

    /**
     * Zeigt an ob der Agent eine Resource sehen kann.
     *
     * @return true oder false.
     */
    boolean seeResource();
    
    /**
     * Zeigt an ob der Agent eine Resource vom angegebenen Typen sehen kann.
     *
     * @return true oder false.
     */
    public boolean seeResource(final Resource.ResourceType resourceType);

    /**
     * Der Agent spendet einem Teammitglied Treibstoff
     *
     * @param value Treibstoff. Ein Agent ist Bewegungsunf&auml;hig, solange er
     * Treibstoff spendet!
     * Aktionspunkte: value * 2
     * Treibstoff: 1 + value
     * @param value Der zu spendende Treibstoff.
     */
    void spendTeamAgentFuel(int value);

    /**
     * Der Agent dreht sich um 180 Grad in die entgegengesetzte Richtung.
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    void turnAround();

    /**
     * Der Agent dreht sich um Winkel beta nach links.
     *
     * @param beta Gew&uuml;nschter Winkel in Grad (0 - 360)
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    void turnLeft(int beta);

    /**
     * Der Agent dreht sich in eine zuf&auml;llige Richtung.
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    void turnRandom();

    /**
     * Der Agent dreht sich in eine beliebige, zufällige Richtung mit dem
     * Offnungswinkel {
     *
     * {@code opening}°. Aktionspunkte: 1
     * Treibstoff: 1
     *
     * @param opening
     */
    void turnRandom(final int opening);

    /**
     * Der Agent dreht sich um Winkel {
     *
     * {@code beta}° nach rechts.
     *
     * @param beta Gew&uuml;nschter Winkel in Grad (0 - 360)
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    void turnRight(int beta);

    /**
     * Der Agent läuft amok.
     */
    void kill();

    /**
     * Überprüft ob dieser Agent in mindestens einer der übergebenen SwatTeams enthalten ist.
     *
     * @param swatTeams
     * @return
     */
    boolean isMemberOfSwatTeam(final SwatTeam... swatTeams);

    /**
     * Überprüft ob dieser Agent in mindestens einer der übergebenen SwatTeams enthalten ist.
     *
     * @param swatTeams
     * @return
     */
    boolean isMemberOfSwatTeam(final Collection<SwatTeam> swatTeams);

    /**
     * Errichtet den Turm an der Position des Kommanders.
     * Diese Aktion kann nur durch den Kommander durchgeführt werden!
     *
     * Es gibt hierbei zwei Arten von Türmen:
     * - Einen Verteidigungsturm (DefenceTower) der feindliche Agenten für alle Agenten des Teams sichtbar macht und diese angreift.
     * - Einen Beobachtungsturm (ObservationTower) der Ressourcen in Reichweite für all Agenten des Teams sichtbar macht und zudem Agenten in Reichweite mit Energie beliefert.
     *
     * @param type Hierrüber kannst du den Turmtypen auwählen welcher errichtet werden soll.
     */
    @Deprecated
    void erectTower(final Tower.TowerType type);
    
    /**
     * Baut einen Turm wieder ab der zuvor aufgestellt wurde..
     * Diese Aktion kann auch wieder nur vom Kommander durchgeführt werden und zwar nur dann wenn er in unmittelbarer Nähe des Turms ist.
     */
    @Deprecated
    void dismantleTower();
    
    /**
     * Überprüft ob dieser Agent einen Turm aufbauen könnte.
     * Bedenke das nur Kommander einen Turm trägt!
     * @return 
     */
    @Deprecated
    boolean hasTower();
}
