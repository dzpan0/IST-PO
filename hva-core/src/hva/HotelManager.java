package hva;

import java.io.*;
import hva.exceptions.*;

/**
 * Class that represents the hotel application.
 */
public class HotelManager {

    /** This is the current hotel. */
    private Hotel _hotel = new Hotel();

    /** The name of the current file that stores hotel. */
    private String _filename = null;

    /**
     * Saves the serialized application's state into the file associated to the current network.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void save() throws FileNotFoundException,
            MissingFileAssociationException, IOException {
        if (_filename == null) {
            throw new MissingFileAssociationException();
        }

        if (_hotel.hasChanged()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                    new FileOutputStream(_filename)))) {
                oos.writeObject(_hotel);
            }

            _hotel.setChanged(false);
        }
    }

    /**
     * Saves the serialized application's state into the file associated to the current network.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void saveAs(String filename)
            throws FileNotFoundException, IOException {
        _filename = filename;

        try {
            save();
        }
        catch (MissingFileAssociationException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filename name of the file containing the serialized application's state
     *        to load.
     * @throws UnavailableFileException if the specified file does not exist or there is
     *         an error while processing this file.
     */
    public void load(String filename) throws UnavailableFileException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(
                new FileInputStream(filename)))) {
            _hotel = (Hotel) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            throw new UnavailableFileException(filename);
        }

        _filename = filename;
        _hotel.setChanged(false);
    }

    /**
     * Read text input file.
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    public void importFile(String filename) throws ImportFileException {
        _hotel.importFile(filename);
    }

    /**
     * @return The current hotel.
     */
    public Hotel getHotel() {
        return _hotel;
    }

    /**
     * Checks if the current hotel has changed
     * since last save or creation.
     *
     * @return the value checked
     */
    public boolean changed() {
        return _hotel.hasChanged();
    }

    /**
     * Advances the season of the current hotel.
     *
     * @return the code of the following season
     */
    public int advanceSeason() {
        return _hotel.advanceSeason();
    }

    public double getGlobalSatisfaction() {
        return _hotel.getGlobalSatisfaction();
    }

    /**
     * Resets the hotel, creating a new one.
     */
    public void reset() {
        _hotel = new Hotel();
        _filename = null;
    }
}
