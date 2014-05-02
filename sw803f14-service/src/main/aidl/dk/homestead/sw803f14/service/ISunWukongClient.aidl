package dk.homestead.sw803f14.service;

/**
 * The SunWukongClient interface makes it simple for clients to define logic for service callbacks.
 **/
interface ISunWukongClient {
    void onClientRegistered();
    void onTransferComplete(String path);
    void onTransferProgress(int current, int max);
}