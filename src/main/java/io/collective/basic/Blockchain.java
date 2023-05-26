package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a blockchain that stores a sequence of blocks.
 */
public class Blockchain {

    /**
     * List to store the blocks in the blockchain
     */
    private final List<Block> mBlocks;

    /**
     * Constructs an empty blockchain.
     */
    public Blockchain() {
        mBlocks = new LinkedList<>();
    }

    /**
     * Checks if the blockchain is empty.
     *
     * @return true if the blockchain is empty, false otherwise.
     */
    public boolean isEmpty() {
        return mBlocks.isEmpty();
    }

    /**
     * Adds a block to the blockchain.
     *
     * @param block The block to be added.
     */
    public void add(Block block) {
        mBlocks.add(block);
    }

    /**
     * Returns the size of the blockchain.
     *
     * @return The number of blocks in the blockchain.
     */
    public int size() {
        return mBlocks.size();
    }

    /**
     * Checks if the blockchain is valid.
     *
     * @return true if the blockchain is valid, false otherwise.
     * @throws NoSuchAlgorithmException if the hashing algorithm is not available.
     */
    public boolean isValid() throws NoSuchAlgorithmException {

        // todo - check an empty chain
        if (mBlocks.isEmpty())
            return true;

        // todo - check a chain of one
        else if (mBlocks.size() == 1) {
            Block firstBlock = mBlocks.get(0);
            return isMined(firstBlock) && firstBlock.getHash().equals(firstBlock.calculatedHash());
        }

        // todo - check a chain of many
        else {
            Block currentBlock;
            Block previousBlock;

            // Iterate through each block in the blockchain
            for (int i = 1; i < mBlocks.size(); i++) {
                currentBlock = mBlocks.get(i);
                previousBlock = mBlocks.get(i - 1);

                // Check if the current block and the previous block are mined
                if (!isMined(currentBlock) || !isMined(previousBlock))
                    return false;

                // Check if the current block's hash is valid
                if (!currentBlock.getHash().equals(currentBlock.calculatedHash()))
                    return false;

                // Check if the previous block's hash is valid
                if (!previousBlock.getHash().equals(previousBlock.calculatedHash()))
                    return false;

                // Check if the current block's previous hash matches the previous block's hash
                if (!currentBlock.getPreviousHash().equals(previousBlock.getHash()))
                    return false;
            }
        }

        return true;
    }

    /// Supporting functions that you'll need.

    /**
     * Mines a block by finding a nonce that satisfies the mining condition.
     *
     * @param block The block to be mined.
     * @return The mined block.
     * @throws NoSuchAlgorithmException if the hashing algorithm is not available.
     */
    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
        }

        return mined;
    }

    /**
     * Checks if a block is mined by checking its hash.
     *
     * @param minedBlock The block to be checked.
     * @return true if the block is mined, false otherwise.
     */
    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
    }
}
