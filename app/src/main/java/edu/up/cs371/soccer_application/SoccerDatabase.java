package edu.up.cs371.soccer_application;

import android.util.Log;

import edu.up.cs371.soccer_application.soccerPlayer.SoccerPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Soccer player database -- presently, all dummied up
 * 
 * @author *** put your name here ***
 * @version *** put date of completion here ***
 *
 */
public class SoccerDatabase implements SoccerDB {

    private Hashtable<String, SoccerPlayer> soccerPlayers = new Hashtable<String, SoccerPlayer>();

    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
	public boolean addPlayer(String firstName, String lastName,
			int uniformNumber, String teamName) {
        if (soccerPlayers.containsKey(firstName + "|" + lastName))
            return false;

        SoccerPlayer sp = new SoccerPlayer(firstName, lastName, uniformNumber, teamName);

        soccerPlayers.put(firstName + "|" + lastName, sp);

        return true;
	}

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @Override
    public boolean removePlayer(String firstName, String lastName) {

        if (soccerPlayers.containsKey(firstName + "|" + lastName))
        {
            soccerPlayers.remove(firstName + "|" + lastName);
            return true;
        }
        return false;
    }


    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
	public SoccerPlayer getPlayer(String firstName, String lastName) {
        String key = firstName + "|" + lastName;
        return soccerPlayers.get(key);
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName) {
        if (searchPlayer(firstName, lastName))
        {
            soccerPlayers.get(firstName + "|" + lastName).bumpGoals();
            return true;
        }

        return false;
    }

    /**
     * increment a player's assists
     *
     * @see SoccerDB#bumpAssists(String, String)
     */
    @Override
    public boolean bumpAssists(String firstName, String lastName) {
        if (searchPlayer(firstName, lastName))
        {
            soccerPlayers.get(firstName + "|" + lastName).bumpAssists();
            return true;
        }

        return false;
    }

    /**
     * increment a player's shots
     *
     * @see SoccerDB#bumpShots(String, String)
     */
    @Override
    public boolean bumpShots(String firstName, String lastName) {
        if (searchPlayer(firstName, lastName))
        {
            soccerPlayers.get(firstName + "|" + lastName).bumpShots();
            return true;
        }

        return false;
    }

    /**
     * increment a player's saves
     *
     * @see SoccerDB#bumpSaves(String, String)
     */
    @Override
    public boolean bumpSaves(String firstName, String lastName) {
        if(this.searchPlayer(firstName,lastName))
        {
            soccerPlayers.get(firstName+"|"+lastName).bumpSaves();
            return true;
        }
        return false;
    }

    /**
     * increment a player's fouls
     *
     * @see SoccerDB#bumpFouls(String, String)
     */
    @Override
    public boolean bumpFouls(String firstName, String lastName) {
        if(this.searchPlayer(firstName,lastName))
        {
            soccerPlayers.get(firstName+"|"+lastName).bumpFouls();
            return true;
        }
        return false;
    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        if(this.searchPlayer(firstName,lastName))
        {
            soccerPlayers.get(firstName+"|"+lastName).bumpYellowCards();
            return true;
        }
        return false;
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        if(this.searchPlayer(firstName,lastName))
        {
            soccerPlayers.get(firstName+"|"+lastName).bumpRedCards();
            return true;
        }
        return false;
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
	public int numPlayers(String teamName) {
        int count = 0;
        if (teamName == null)
        {
            // get all table elements
            for (SoccerPlayer sp : soccerPlayers.values())
            {
                count++;
            }
        }
        else
        {
            // get team specific table elements
            for (SoccerPlayer sp : soccerPlayers.values())
            {
                if ((sp.getTeamName()).equals(teamName))
                {
                    count++;
                }
            }
        }

        return count;
	}

    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerNum(int, String)
     */
	// get the nTH player
	@Override
    public SoccerPlayer playerNum(int idx, String teamName) {
        int count = 0;
        if(teamName == null)
        {
            for (SoccerPlayer sp : soccerPlayers.values())
            {
                if (count >= idx)
                    return sp;

                count++;
            }
        }
        else
        {
            for (SoccerPlayer sp : soccerPlayers.values())
            {
                if ((sp.getTeamName()).equals(teamName))
                {
                    if (count >= idx)
                        return sp;

                    count++;
                }
            }
        }
        return null;
    }

    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
	// read data from file
    @Override
	public boolean readData(File file) {

        try
        {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream sln = new ObjectInputStream(fis);
            while(true) {
                sln.read();
            }

        }
        catch(FileNotFoundException fnfe)
        {

        }
        catch(IOException ioe)
        {

        }
        return file.exists();
	}

    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(java.io.File)
     */
	// write data to file
    @Override
	public boolean writeData(File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(fos);

            for (SoccerPlayer sp : soccerPlayers.values())
            {
                pw.println(logString(sp.getFirstName()));
                pw.println(logString(sp.getLastName()));
                pw.println(logString(sp.getTeamName()));
                pw.println(logString(sp.getUniform() + ""));
                pw.println(logString(sp.getGoals() + ""));
                pw.println(logString(sp.getAssists() + ""));
                pw.println(logString(sp.getShots() + ""));
                pw.println(logString(sp.getSaves() + ""));
                pw.println(logString(sp.getFouls() + ""));
                pw.println(logString(sp.getYellowCards() + ""));
                pw.println(logString(sp.getRedCards() + ""));
            }

            fos.close();
        } catch (FileNotFoundException fnfe)
        {
            // file not found
            return false;
        } catch (IOException ioe)
        {
            return false;
        }

        return true;
	}

    /**
     * helper method that logcat-logs a string, and then returns the string.
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
        Log.i("write string", s);
        return s;
    }

    /**
     * returns the list of team names in the database
     *
     * @see edu.up.cs371.soccer_application.SoccerDB#getTeams()
     */
	// return list of teams
    @Override
	public HashSet<String> getTeams() {
        String temp;
        HashSet<String> teamNames = new HashSet<>();
        for (SoccerPlayer sp: soccerPlayers.values()) {
            temp = sp.getTeamName();
            if(teamNames.contains(temp))
            {
                //do nothing
            }
            else
            {
                teamNames.add(temp);
            }

        }
        return teamNames;
	}

    private boolean searchPlayer(String first, String last)
    {
        return (soccerPlayers.containsKey(first + "|" + last));
    }

}
