
async function getDogs(){
  const response = await fetch('https://dog.ceo/api/breeds/image/random')
  const data = await response.json()
  return data
}

export default async function Home() {
  const dogs = await getDogs()
  console.log(dogs)
  return (

    <div className="text-center mt-20">
      <h1 className="text-5xl font-bold">Home</h1>
      <img src = {dogs.message} width={500} height={500}/>
    </div>
    
  );
}
