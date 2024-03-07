from repo.playlist_repo import PlaylistFileRepo

 class PlaylistService:
     def __init__(self,repo):
         self.__repo=repo
     def get_all_playlists(self):
         return self.__repo.get_all()
     def cautare_playlist(self,titlu):
         playlists=self.__repo.get_all()
         lista=[]
         for playlist in playlists:
             if playlist.getTitlu()==titlu:
                 lista.append(playlist)
         return lista
     def ascultare_playlist(self,playlist,start_interval,stop_interval):