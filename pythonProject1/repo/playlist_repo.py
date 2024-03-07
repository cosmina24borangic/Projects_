from domain.entities import Playlist

class PlaylistInMemoryRepo:
    def __init__(self):
        self.__playlist_uri=[]
    def size(self):
        return len(self.__playlist_uri)
    def get_all_playlists(self):
        return self.__playlist_uri
class PlaylistFileRepo:
    def __init__(self,filename):
        self.__filename=filename
    def __load_from_file(self):
        try:
            f=open(self.__filename,'r')
        except IDError:
            return
        lines=f.readlines()
        all_playlist=[]
        for line in lines:
            id_playlist,titlu,data,durata=[token.strip() for token in line.split(',')]
            p=Playlist(id_playlist,titlu,data,int(durata))
            all_playlist.append(p)
        f.close()
        return all_playlist
    def __save_to_file(self,all_playlist):
        with open(self.__filenames,'w') as f:
            for play in all_playlist:
                play_string=str(play.getPlaylist_id())+';'+str(play.getTitlu())+';'+str(play.getData_creare())+';'+str(play.getDurata_totala) +'\n'
    def get_all(self):
        return self.__load_from_file()