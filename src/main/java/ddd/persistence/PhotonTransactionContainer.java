package ddd.persistence;

import com.github.molcikas.photon.Photon;
import com.github.molcikas.photon.PhotonTransaction;

public class PhotonTransactionContainer implements TransactionContainer
{
    private final PhotonTransaction photonTransaction;

    /**
     * Call this in the repository constructor get retrieve the PhotonConnection object, then use it to perform queries. DO
     * NOT CALL COMMIT OR CLOSE FROM THE REPOSITORY! The UnitOfWork will commit the transaction.
     *
     * @return
     */
    public PhotonTransaction getPhotonTransaction()
    {
        return photonTransaction;
    }

    public PhotonTransactionContainer(Photon photon)
    {
        if(photon == null)
        {
            throw new IllegalArgumentException("photon cannot be null.");
        }
        this.photonTransaction = photon.beginTransaction();
    }

    @Override
    public void commit()
    {
        photonTransaction.commit();
    }

    @Override
    public void close()
    {
        if(photonTransaction.hasUncommittedChanges())
        {
            photonTransaction.rollback();
        }
        photonTransaction.close();
    }
}
