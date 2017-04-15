package ddd.persistence;

import photon.Photon;
import photon.PhotonConnection;

public class PhotonTransactionContainer implements TransactionContainer
{
    private final PhotonConnection photonConnection;

    /**
     * Call this in the repository constructor get retrieve the PhotonConnection object, then use it to perform queries. DO
     * NOT CALL COMMIT OR CLOSE FROM THE REPOSITORY! The UnitOfWork will commit the transaction.
     *
     * @return
     */
    public PhotonConnection getPhotonConnection()
    {
        return photonConnection;
    }

    public PhotonTransactionContainer(Photon photon)
    {
        if(photon == null)
        {
            throw new IllegalArgumentException("photon cannot be null.");
        }
        this.photonConnection = photon.beginTransaction();
    }

    @Override
    public void commit()
    {
        photonConnection.commit();
    }

    @Override
    public void close()
    {
        if(photonConnection.isOpen())
        {
            photonConnection.rollbackTransaction();
        }
        photonConnection.close();
    }
}
