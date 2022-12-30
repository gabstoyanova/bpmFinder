import {useState} from "react";

function Track() {

  const [track, setTrack] = useState();

  const getTrack = async () => {
    fetch("http://localhost:8080/track/" + document.getElementsByClassName("search")[0].value)
      .then(response => response.json())
      .then(data => {
        console.log(data)
        setTrack(data)
      })
    // TODO add error handling
  }

  return (
    <div>
      <h1>This is the get track page</h1>
      <input
        className="search"
        type="text"
        placeholder="Search by track id lol ..."
      />
      <button className="SearchButton" onClick={getTrack}>Search</button>
      {track != null &&
        <div>
          <p>i did it</p>
          {track.name}
        </div>
      }
    </div>
  )
}

export default Track