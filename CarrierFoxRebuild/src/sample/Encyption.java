package sample;
/*
import org.whispersystems.signalservice.*;
import org.whispersystems.signalservice.api.SignalServiceAccountManager;
import org.whispersystems.signalservice.api.push.TrustStore;

public class Encyption
{
    private final String     URL         = "https://my.signal.server.com";
    private final TrustStore TRUST_STORE = new MyTrustStoreImpl();
    private final String     USERNAME    = "+14151231234";
    private final String     PASSWORD    = generateRandomPassword();
    private final String     USER_AGENT  = "[FILL_IN]";

    public void createKeys()
    {
        IdentityKeyPair    identityKey        = KeyHelper.generateIdentityKeyPair();
        List<PreKeyRecord> oneTimePreKeys     = KeyHelper.generatePreKeys(0, 100);
        PreKeyRecord       lastResortKey      = KeyHelper.generateLastResortPreKey();
        SignedPreKeyRecord signedPreKeyRecord = KeyHelper.generateSignedPreKey(identityKey, signedPreKeyId);
    }

    public void registeringKeys()
    {
        SignalServiceAccountManager accountManager = new SignalServiceAccountManager(URL, TRUST_STORE,
                USERNAME, PASSWORD, USER_AGENT);

        accountManager.requestSmsVerificationCode();
        accountManager.verifyAccountWithCode(receivedSmsVerificationCode, generateRandomSignalingKey(),
                generateRandomInstallId(), false);
        accountManager.setGcmId(Optional.of(GoogleCloudMessaging.getInstance(this).register(REGISTRATION_ID)));
        accountManager.setPreKeys(identityKey.getPublicKey(), lastResortKey, signedPreKeyRecord, oneTimePreKeys);
    }
}*/
