class Playlist:
    def __init__(self,playlist_id,titlu,data_creare,durata_totala):
        """
        Creeaza  un nou playlist cu id, titlu, data si durata totala
        :type playlist_id:str
        :param playlist_id:id-ul playlist -ului
        :type titlu:str
        :param titlu:titlul playlist -ului
        :type data_creare:date
        :param data_creare: data de creare a playlist-ului
        :type durata_totala:int
        :param durata_totala:durata playlist-ului
        """
        self.__playlist_id=playlist_id
        self.__titlu = titlu
        self.__data_creare = data_creare
        self.__durata_totala = durata_totala

    def getPlaylist_id(self):
        return self.__playlist_id
    def getTitlu(self):
        return self.__titlu
    def getData_creare(self):
        return self.__data_creare
    def getDurata_totala(self):
        return self.__durata_totala
    def setPlaylist_id(self,value):
        self.__playlist_id=value
    def setTitlu(self,value):
        self.__titlu=value
    def setData_creare(self,value):
        self.__data_creare=value
    def setDurata_totala(self,value):
        self.__durata_totala=value
    def __eq__(self,other):
        if self.__playlist_id==other.getPlaylist_id():
            return True
        return False
    def __str__(self):
        return "ID:"+str(self.getPlaylist_id)+"Titlu:"+str(self.getTitlu)+"Data creare:"+str(self.getData_creare)+"Durata:"+str(self.getDurata_totala)