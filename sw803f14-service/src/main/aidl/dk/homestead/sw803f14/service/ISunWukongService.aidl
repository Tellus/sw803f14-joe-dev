package dk.homestead.sw803f14.service;

import dk.homestead.sw803f14.service.ISunWukongTransferListener;

interface ISunWukongService {
    String getGlobalMessage();
    void setGlobalMessage(String msg);
    void registerClient(String name);
    void retrieveBlock(int blockId, ISunWukongTransferListener listener);
}