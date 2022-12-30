import { Link } from "react-router-dom";

function Home() {

  return (
    <div>
      <h1>My experiments with react</h1>
      <Link to="track">Click to view track page</Link>

      {/*components part of other components: */}
      {/*<Track/>*/}

    </div>
  );
}

export default Home;