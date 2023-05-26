package io.collective.basic;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Represents a block in a blockchain.
 */
public class Block {

    // Hash of the previous block
    private String previousHash;

    // Timestamp of the block
    private long timestamp;

    // Nonce value used in mining
    private int nonce;

    // Hash value of the block
    private String hash;

    /**
     * Constructs a new block with the given parameters.
     *
     * @param previousHash The hash of the previous block.
     * @param timestamp The timestamp of the block.
     * @param nonce The nonce value used in mining.
     * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not available.
     */
    public Block(String previousHash, long timestamp, int nonce) throws NoSuchAlgorithmException {
        this.previousHash = previousHash;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.hash = calculatedHash(); // Calculate the hash value for the block
    }

    /**
     * Returns the hash of the previous block.
     *
     * @return The hash of the previous block.
     */
    public String getPreviousHash() {
        return previousHash;
    }

    /**
     * Returns the timestamp of the block.
     *
     * @return The timestamp of the block.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the nonce value used in mining.
     *
     * @return The nonce value used in mining.
     */
    public int getNonce() {
        return nonce;
    }

    /**
     * Returns the hash value of the block.
     *
     * @return The hash value of the block.
     */
    public String getHash() {
        return hash;
    }

    /**
     * Calculates the hash value for the block.
     *
     * @return The calculated hash value for the block.
     * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not available.
     */
    public String calculatedHash() throws NoSuchAlgorithmException {
        return calculateHash(previousHash + timestamp + nonce);
    }

    /// Supporting functions that you'll need.

    /**
     * Calculates the SHA-256 hash value of a string.
     *
     * @param string The input string.
     * @return The calculated SHA-256 hash value.
     * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not available.
     */
    static String calculateHash(String string) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(string.getBytes());
        return String.format("%064x", new BigInteger(1, digest.digest()));
    }
}
